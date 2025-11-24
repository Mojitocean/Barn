package com.barn.product.adapter.rpc;

import com.barn.api.product.dto.SkuInfoDTO;
import com.barn.api.product.dto.StockDeductionDTO;
import com.barn.api.product.service.ProductServiceApi;
import com.barn.core.domain.R;
import com.barn.core.exception.BizException;
import com.barn.product.domain.entity.Sku;
import com.barn.product.domain.repo.ProductRepository;
import com.barn.product.domain.service.StockDomainService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * packageName com.barn.product.adapter.rpc
 * <p>
 * 商品服务eProvider
 *
 * @author mj
 * @className ProductServiceProvider
 * @date 2025/11/22
 * @description TODO
 */
@DubboService
public class ProductServiceProvider implements ProductServiceApi {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockDomainService stockDomainService;

    @Override
    public R<SkuInfoDTO> getSkuInfo(Long skuId) {
        Sku sku = productRepository.findSkuById(skuId);
        if (sku == null) {
            return R.fail("商品不存在");
        }
        // Entity -> DTO (实际项目中推荐使用 MapStruct)
        SkuInfoDTO dto = new SkuInfoDTO();
        dto.setId(sku.getId());
        dto.setSpuId(sku.getSpuId());
        dto.setSkuName(sku.getSkuName());
        dto.setPrice(sku.getPrice());
        dto.setStock(sku.getStock());
        dto.setSpecJson(sku.getSpecJson());

        return R.ok(dto);
    }

    @Override
    @Transactional(rollbackFor = BizException.class)
    public R<Boolean> deductStock(List<StockDeductionDTO> deductionList) {
        try {
            // 转换参数 List -> Map<SkuId, Count>
            Map<Long, Integer> skuCountMap = deductionList.stream().collect(Collectors.toMap(StockDeductionDTO::getSkuId, StockDeductionDTO::getCount));
            // 调用领域服务执行核心逻辑
            stockDomainService.deductStock(skuCountMap);
            return R.ok(true);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}