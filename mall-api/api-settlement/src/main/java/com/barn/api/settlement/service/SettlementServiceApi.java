package com.barn.api.settlement.service;


import com.barn.api.settlement.dto.SettlementRequestDTO;
import com.barn.api.settlement.dto.SettlementResultDTO;
import com.barn.core.domain.R;


/**
 * packageName com.barn.api.settlement.service
 * 【外部接口】结算服务
 * 职责：集中处理所有复杂的定价、促销、会员折扣、运费、优惠券核算逻辑
 *
 * @author mj
 * @className SettlementServiceApi
 * @date 2025/11/24
 * @description TODO
 */
public interface SettlementServiceApi {

    /**
     * 根据下单请求计算出最终的结算结果（包含所有折扣、运费和最终支付价）
     *
     * @param request 结算请求，包含SKU列表、用户ID、优惠券ID等
     * @return 最终结算结果
     */
    R<SettlementResultDTO> calculateSettlement(SettlementRequestDTO request);
}