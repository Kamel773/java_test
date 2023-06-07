public Object list(Integer userId,Integer showType,Integer page,Integer limit,String sort,String order){
  if (userId == null) {
    return ResponseUtil.unlogin();
  }
  List<Short> orderStatus=OrderUtil.orderStatus(showType);
  List<LitemallOrder> orderList=orderService.queryByOrderStatus(userId,orderStatus,page,limit,sort,order);
  List<Map<String,Object>> orderVoList=new ArrayList<>(orderList.size());
  for (  LitemallOrder o : orderList) {
    Map<String,Object> orderVo=new HashMap<>();
    orderVo.put("id",o.getId());
    orderVo.put("orderSn",o.getOrderSn());
    orderVo.put("actualPrice",o.getActualPrice());
    orderVo.put("orderStatusText",OrderUtil.orderStatusText(o));
    orderVo.put("handleOption",OrderUtil.build(o));
    orderVo.put("aftersaleStatus",o.getAftersaleStatus());
    LitemallGroupon groupon=grouponService.queryByOrderId(o.getId());
    if (groupon != null) {
      orderVo.put("isGroupin",true);
    }
 else {
      orderVo.put("isGroupin",false);
    }
    List<LitemallOrderGoods> orderGoodsList=orderGoodsService.queryByOid(o.getId());
    List<Map<String,Object>> orderGoodsVoList=new ArrayList<>(orderGoodsList.size());
    for (    LitemallOrderGoods orderGoods : orderGoodsList) {
      Map<String,Object> orderGoodsVo=new HashMap<>();
      orderGoodsVo.put("id",orderGoods.getId());
      orderGoodsVo.put("goodsName",orderGoods.getGoodsName());
      orderGoodsVo.put("number",orderGoods.getNumber());
      orderGoodsVo.put("picUrl",orderGoods.getPicUrl());
      orderGoodsVo.put("specifications",orderGoods.getSpecifications());
      orderGoodsVo.put("price",orderGoods.getPrice());
      orderGoodsVoList.add(orderGoodsVo);
    }
    orderVo.put("goodsList",orderGoodsVoList);
    orderVoList.add(orderVo);
  }
  return ResponseUtil.okList(orderVoList,orderList);
}
