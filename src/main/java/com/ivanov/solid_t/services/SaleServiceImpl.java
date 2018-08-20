package com.ivanov.solid_t.services;

import com.ivanov.solid_t.dto.StatisticsDTO;
import com.ivanov.solid_t.entities.Sale;
import com.ivanov.solid_t.entities.SalePosition;
import com.ivanov.solid_t.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SaleServiceImpl implements SaleService {

    private static final int HOUR_INDEX = 0;
    private static final int ID_INDEX = 1;

    @PersistenceContext
    private EntityManager em;

    private SaleRepository saleRepository;

    @Autowired
    public void setSaleRepository(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Iterable<Sale> listAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(Integer id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public void deleteSale(Integer id) {
        saleRepository.deleteById(id);
    }

    @Override
    public List<StatisticsDTO> getStatistics() {
        Query q = em.createNativeQuery(
                "select date_trunc('hour', date), id from sale order by date;");
        List<Object[]> result = q.getResultList();

        Map<LocalDateTime, List<Integer>> prepareStatistics = new TreeMap<>();
        result.forEach(record -> {
            LocalDateTime hour = ((Timestamp) record[HOUR_INDEX]).toLocalDateTime();
            List<Integer> saleIds = prepareStatistics.computeIfAbsent(hour, k -> new ArrayList<>());
            saleIds.add(Integer.parseInt(record[ID_INDEX].toString()));
        });

        return buildStatisticsDTOList(prepareStatistics);
    }

    private List<StatisticsDTO> buildStatisticsDTOList(Map<LocalDateTime, List<Integer>> prepareStatistics) {
        List<StatisticsDTO> statisticsDTOList = new ArrayList<>();
        for (Map.Entry<LocalDateTime, List<Integer>> entry : prepareStatistics.entrySet()) {
            LocalDateTime hour = entry.getKey();
            List<Integer> saleIds = entry.getValue();
            List<Sale> sales = new ArrayList<>();
            saleRepository.findAllById(saleIds).forEach(sales::add);

            statisticsDTOList.add(buildStatisticsDTO(hour, sales));
        }
        return statisticsDTOList;
    }

    private StatisticsDTO buildStatisticsDTO(LocalDateTime hour, List<Sale> sales) {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal sumOfDiscounts = new BigDecimal(0);
        BigDecimal sumWithDiscounts = new BigDecimal(0);

        for (Sale sale : sales) {
            List<SalePosition> salePositions = sale.getSalePositions();

            for (SalePosition salePosition : salePositions) {
                BigDecimal position = salePosition.getProduct().getPrice();
                BigDecimal quantity = new BigDecimal(salePosition.getQuantity());

                BigDecimal sumOfPosition = position.multiply(quantity);

                sum = sum.add(sumOfPosition);

                if (salePosition.getDiscount() != null) {
                    BigDecimal onePercent = position.divide(new BigDecimal(100), RoundingMode.HALF_UP);
                    BigDecimal discount = new BigDecimal(salePosition.getDiscount().getValue()).multiply(onePercent);

                    sumOfDiscounts = sumOfDiscounts.add(discount.multiply(quantity));
                    sumWithDiscounts = sumWithDiscounts.add(position.subtract(discount).multiply(quantity));
                } else {
                    sumWithDiscounts = sumWithDiscounts.add(sumOfPosition);
                }

            }
        }

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setHour(hour);
        statisticsDTO.setSaleQuantity(sales.size());
        statisticsDTO.setSum(sum);
        statisticsDTO.setAverage(sum.divide(new BigDecimal(sales.size()), RoundingMode.HALF_UP));
        statisticsDTO.setSumOfDiscounts(sumOfDiscounts);
        statisticsDTO.setSumWithDiscounts(sumWithDiscounts);
        statisticsDTO.setAverageWithDiscounts(sumWithDiscounts.divide(
                new BigDecimal(sales.size()), RoundingMode.HALF_UP));

        return statisticsDTO;
    }

    @Override
    public void saveAllSales(List<Sale> sales) {
        saleRepository.saveAll(sales);
    }

    @Override
    public void deleteAllSales() {
        saleRepository.deleteAll();
    }

}
