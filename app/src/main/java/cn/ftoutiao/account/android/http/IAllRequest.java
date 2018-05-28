package cn.ftoutiao.account.android.http;


import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.ConfigBean;
import cn.ftoutiao.account.android.model.ImageHeaderEntiry;
import cn.ftoutiao.account.android.model.ItemListEntity;
import cn.ftoutiao.account.android.model.ListEntity;
import cn.ftoutiao.account.android.model.MembersBo;
import cn.ftoutiao.account.android.model.NoteBookEntity;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.SaltEntity;
import cn.ftoutiao.account.android.model.ShareNoteBo;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.model.db.ATypeListEntity;
import cn.ftoutiao.account.android.model.db.CategoryEntity;
import cn.ftoutiao.account.android.model.db.ListItemBO;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/1/3 15:13
 * @detail 服务接口定义
 */
public interface IAllRequest {
//    /**
//     * 时间同步
//     */
//    @POST()
//    Call<SucceedEntity> refreshtime(@Url String url);
//
//    /**
//     * App配置
//     */
//    @POST()
//    Call<ConfigEntity> config(@Url String url);
//
//    /**
//     * 广告
//     */
//    @POST()
//    Call<CoopenEntity> coopen(@Url String url);
//
//    /**
//     * 查询是否登录
//     */
//    @POST()
//    Call<UserEntity> islogin(@Url String url);
//

    /**
     * 密码盐-登录初始化
     */
    @FormUrlEncoded
    @POST()
    Call<SaltEntity> initlogin(@Url String url, @Field("loginname") String name, @Field("type") String accounttype);


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST()
    Call<UserEntity> dologin(@Url String url,
                             @Field("loginname") String name,
                             @Field("loginpwd") String pwd);

    /**
     * 三方登录
     */
    @FormUrlEncoded
    @POST()
    Call<UserEntity> authorLogin(@Url String url,
                                 @Field("openId") String openId,
                                 @Field("authToken") String authToken,
                                 @Field("platform") String platform,
                                 @Field("refreshToken") String refreshToken,
                                 @Field("unionId") String unionId);


    /**
     * app获取短信验证码
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> getmobilevcodewith(@Url String url,
                                        @Field("mobile") String mobile,
                                        @Field("type") String type
    );
//
//    /**
//     * 图形验证码-图形验证码令牌
//     */
//    @POST()
//    Call<PicCaptchaSignEntity> getPicCaptchaSign(@Url String url, @Query(UrlConstans.IMEI) String imei);
//
//    /**
//     * 图形验证码-获取图片
//     */
//    @POST()
//    Call<Bitmap> picCaptcha(@Url String url,
//                            @Query(UrlConstans.PC_SIGN) String pcsign,
//                            @Query(UrlConstans.PC_STS) long pcsts,
//                            @Query(UrlConstans.PC_CHANNEL) String pcChannel,
//                            @Query(UrlConstans.PC_UID) String pcuid);
//
//    /**
//     * 密码盐-注册初始化
//     */
//    @POST()
//    Call<SaltEntity> initregister(@Url String url);
//

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST()
    Call<UserEntity> register(@Url String url,
                              @Field("mobile") String mobileno,
                              @Field("password") String saltpwd,
                              @Field("salt") String salt,
                              @Field("mcode") String mvcode
    );


    /**
     * 得到用户信息
     */
    @POST()
    Call<UserEntity> requestUserInfo(@Url String url);


    @FormUrlEncoded
    @POST()
    Call<ListEntity> addNoteBook(@Url String url,
                                 @Field("aType") int aType,
                                 @Field("aname") String aname);

    @FormUrlEncoded
    @POST()
    Call<ListEntity> updateNoteBook(@Url String url,
                                    @Field("aId") String aid,
                                    @Field("aname") String aname);

    /**
     * 添加类别
     */
    @FormUrlEncoded
    @POST()
    Call<CategoryEntity> addCategory(@Url String url,
                                     @Field("aId") String aId,
                                     @Field("rId") int rId,
                                     @Field("cName") String cName,
                                     @Field("color") String color,
                                     @Field("icon") String icon);


    @FormUrlEncoded
    @POST()
    Call<ItemListEntity> requestItems(@Url String url,

                                      @Field("aId") String name);

    //请求账本所有人
    @FormUrlEncoded
    @POST()
    Call<MembersBo> requestMembers(@Url String url,
                                   @Field("aId") String name);

    /**
     * 分享
     */
    @FormUrlEncoded
    @POST()
    Call<ShareNoteBo> shareNoteBook(@Url String url,
                                    @Field("aId") String name);

    /***
     *
     * @param url
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> exitNoteBook(@Url String url,
                                  @Field("aId") String name);

    /***
     *
     * @param url
     * @param itemId
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> requestDelItem(@Url String url,
                                    @Field("itemId") String itemId);


    /**
     * 修改账本
     *
     * @param url
     * @param name
     * @param uid
     * @param perm
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> modifyBookPer(@Url String url,
                                   @Field("aId") String name,
                                   @Field("uid") String uid,
                                   @Field("perm") int perm);

    /**
     * 删除账本
     *
     * @param url
     * @param aid
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> delNoteBook(@Url String url,
                                 @Field("aId") String aid);


    @FormUrlEncoded
    @POST()
    Call<NullEntity> quiteNoteBook(@Url String url,
                                   @Field("aId") String aid);


    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> updatepwd(@Url String url,
                               @Field("loginname") String name,
                               @Field("mcode") String accounttype,
                               @Field("newPwd") String saltpwd,
                               @Field(UrlConstans.SALT) String salt);

    /**
     * 密码盐-修改密码初始化
     */
//    @POST()
//    Call<SaltEntity> initeditpwd(@Url String url);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST()
    Call<NullEntity> modifyPwd(@Url String url,
                               @Field("loginname") String loginName,
                               @Field("oldPwd") String oldpwd,
                               @Field("newPwd") String newpwd,
                               @Field("salt") String salt
    );

    /**
     * 账本类别
     */
    @FormUrlEncoded
    @POST()
    Call<NoteBookEntity> requestNotebookCatagory(@Url String url,
                                                 @Field("aId") String aId);


//
//    /**
//     * 首页
//     */
//    @POST()
//    Call<HomeEntity> index(@Url String url);
//
//    /**
//     * 我的
//     */
//    @POST()
//    Call<MineEntity> myacount(@Url String url);
//
//    /**
//     * 回款计划+回款历史
//     */
//    @POST()
//    Call<ReturnedMoneyPlanEntity> myPayment(@Url String url, @Query(UrlConstans.MYPAYMENT_TYPE) int type);
//
//    /**
//     * 查询余额
//     */
//    @POST()
//    Call<BalanceResponse> queryAccountBalance(@Url String url);
//
//    /**
//     * 查询个人绑卡信息
//     */
//    @POST()
//    Call<BankCardManagerResponse> queryBindCardsHttps(@Url String url);
//
//    /**
//     * 充值短信重发
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<ReSMSResponse> reSendSmMessage(@Url String url, @Field(UrlConstans.ORDER) String order);
//
//
//    /**
//     * 充值状态查询
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<RechangeQueryResponse> queryRechangeState(@Url String url, @Field(UrlConstans.BIZID) String bizid);
//
//
//    /**
//     * 充值短信确认
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<ReConfirmResponse> rechargeConfirm(@Url String url,
//                                            @Field(UrlConstans.ORDER) String order,
//                                            @Field(UrlConstans.AMOUNT) String amount,
//                                            @Field(UrlConstans.CLIENT_CODE) String password,
//                                            @Field(UrlConstans.TIME) long time,
//                                            @Field(UrlConstans.RANDOM) long random);
//
//
//    /**
//     * 继续充值
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<RechargeResponse> rechargeRequest(@Url String url,
//                                           @Field(UrlConstans.CARDID2) String nusCard,
//                                           @Field(UrlConstans.AMOUNT) String amount,
//                                           @Field(UrlConstans.TIME) long time,
//                                           @Field(UrlConstans.RANDOM) long random);
//
//    /**
//     * 申请提现
//     *
//     * @param nusCard
//     * @param amount
//     * @return
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<OutRechargeResponse> applyWithdraw(@Url String url, @Field(UrlConstans.CARDID) String nusCard,
//                                            @Field(UrlConstans.AMOUNT) String amount,
//                                            @Field(UrlConstans.TIME) long time,
//                                            @Field(UrlConstans.RANDOM) long random);
//
//    /**
//     * 个人中心页
//     */
//    @POST()
//    Call<OwnCenterEntity> ownCenterDataRequest(@Url String url);
//
//    /**
//     * 实名认证 流程
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<RealnameResponse> realNameRequest(@Url String url, @Field("realname") String realname, @Field("idnumber") String idnumber);
//
//    /**
//     * 实名认证查询
//     */
//    @POST()
//    Call<RealNameEntity> realNameInfoRequest(@Url String url);
//
//    /**
//     * 修改密码
//     */
//    @POST()
//    Call<EmptyDataEntity> changePasswordRequest(@Url String url);
//
//    /**
//     * 银行卡信息查询
//     */
//    @POST()
//    Call<BankCardInfoE> getBankCardInfoRequest(@Url String url);
//
//    /**
//     * 修改银行卡信息
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<EmptyDataEntity> amendBankCardRequest(@Url String url, @Field("cardTail") String cardTail,
//                                               @Field("province") String province, @Field("city") String city, @Field("branchBank")
//                                                       String branchBank, @Field("subBank") String subBank);
//
//    /**
//     * 更换银行卡
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<EmptyDataEntity> changeBankCardRequest(@Url String url, @Field("old_cardTail") String old_cardTail
//            , @Field("cardNo") String cardNo, @Field("bt") String bt, @Field("mobile") String mobile);
//
//    /**
//     * 理財產品列表
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<ProductEntity> productListRequest(@Url String url, @Field(UrlConstans.PAGENUMBER) int pagenumber);
//
//
//    /**
//     * 产品详情页面
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<ProductDescResponse> productDescRequest(@Url String url, @Field(UrlConstans.THUMBNAIL_W) int thumbwidth, @Field(UrlConstans.THUMBNAIL_H) int thumbheigh, @Field(UrlConstans.PRODUCTID) String productid);
//
//    /**
//     * 投资记录页面
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<AssetRecordResponse> assetRecordRequest(@Url String url, @Field(UrlConstans.BID_ID) String bid_id, @Field(UrlConstans.PAGENUMBER) int pageid, @Field(UrlConstans.PAGESIZE) int pagesize);
//
//    /**
//     * 交易记录页面
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<TransactionRecordResponse> transRecordRequest(@Url String url, @Field(UrlConstans.PAGENUMBER) int pageid, @Field(UrlConstans.PAGESIZE) int pagesize);
//
//    @POST()
//    Call<BankItemResponse> querySupportBankList(@Url String url);
//
//    /**
//     * 个人绑卡列表
//     */
//    @POST()
//    Call<BankCardManagerResponse> queryBankList(@Url String url);
//
//    /**
//     * 添加银行卡
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<BankItemResponse> addBankCardRequest(@Url String url, @Field("cardNo") String cardNo,
//                                              @Field(UrlConstans.MOBILE) String mobile,
//                                              @Field("bt") String bt);
//
//    /**
//     * 充值卡升级为重提卡
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<UpRwCardResponse> upgradeRechargeCardHttps(@Url String url, @Field("cardNo") String cardNo,
//                                                    @Field(UrlConstans.MOBILE) String mobile,
//                                                    @Field("bt") String bt);
//

    /**
     * 退出登录
     */

    @POST()
    Call<NullEntity> exitUserAccountRequest(@Url String url);


    @Multipart
    @POST()
    Call<ImageHeaderEntiry> undateImage(@Url String url, @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST()
    Call<NullEntity> modifyImageHeader(@Url String url,
                                       @Field("photo") String photo);


    /**
     * 查询排序类别
     *
     * @param url
     * @return
     */
    @POST()
    Call<ATypeListEntity> querySortCategoryList(@Url String url);


    /**
     * 保存排序
     *
     * @param url
     * @param
     * @param incomeSeq1
     * @param incomeSeq2
     * @param outgoSeq1
     * @param outgoSeq2
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ATypeListEntity> requestSortCategory(@Url String url,
                                              @Field("aId") int aId,
                                              @Field("incomeSeq1") String incomeSeq1,
                                              @Field("incomeSeq2") String incomeSeq2,
                                              @Field("outgoSeq1") String outgoSeq1,
                                              @Field("outgoSeq2") String outgoSeq2);

    /**
     * 添加item
     *
     * @param url
     * @param aId
     * @param cType
     * @param cId
     * @param aDate
     * @param amount
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ListItemBO> addItem(@Url String url,
                             @Field("aId") String aId,
                             @Field("cType") int cType,
                             @Field("cId") int cId,
                             @Field("aDate") String aDate,
                             @Field("amount") String amount,
                             @Field("remark") String remark);

    @FormUrlEncoded
    @POST
    Call<ListItemBO> modifyItem(@Url String url,
                                @Field("itemId") String aId,
                                @Field("cType") int cType,
                                @Field("cId") int cId,
                                @Field("aDate") String aDate,
                                @Field("amount") String amount,
                                @Field("remark") String remark);


    /**
     * 修改昵称
     *
     * @param url
     * @param nickname
     * @return
     */

    @FormUrlEncoded
    @POST
    Call<NullEntity> requestModifyNick(@Url String url,
                                       @Field("nickname") String nickname);

    /**
     * 检查配置文件
     *
     * @param url
     * @return
     */
    @POST
    Call<NullEntity> checkConfig(@Url String url);

    /**
     * 检查更新
     *
     * @param url
     * @return
     */

    @POST
    Call<ConfigBean> checkUpgrade(@Url String url);


//
//
//    /**
//     * 查询实名绑卡信息  个人中心，购买产品界面
//     */
//    @POST()
//    Call<QueryRealnameOrBandCardInfo> queryRealNameOrBindCardInfo(@Url String url);
//
//    /**
//     * 我的投资汇总
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<InvestDataListEntity> investmentListDataRequest(@Url String url,
//                                                         @Field("type") int type, @Field("pn") int pn, @Field("ps") int ps);
//
//    /**
//     * 我的投资列表
//     */
//    @POST()
//    Call<InvestSummaryEntity> myInvestmentSummaryRequest(@Url String url);
//
//    /**
//     * 我的投资详情
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<InvestDetailsEntity> investmentDetailsRequest(@Url String url, @Field("invest_id") String invest_id);
//
//    /**
//     * 查看合同是否可以下载
//     */
//    @POST()
//    Call<ContractEntity> checkContractIsAvailableRequest(@Url String url);
//
//    /**
//     * 购买产品-标的详情
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<BuyProductDetailResponse> queryBidDetail(@Url String url, @Field("id") long id);
//
//    /**
//     * 购买产品
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<BuyProductRespond> buyProduct(@Url String url, @Field("bid_id") long id,
//                                       @Field(UrlConstans.AMOUNT) String amount,
//                                       @Field("client") int client,
//                                       @Field(UrlConstans.V_CODE) String vertifyCode);
//
//    /**
//     * 获取风险测评状态
//     */
//    @POST()
//    Call<EvaluateResponse> queryRiskScore(@Url String url);
//
//    /**
//     * 上报风险评测分数
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<EvaluateResponse> uploadRiskScore(@Url String url, @Field("score") String score);
//
//    /**
//     * 获取推荐人手机号
//     */
//    @POST()
//    Call<RecommendMobileEntity> recommendMobile(@Url String url);
//
//    /**
//     * 更新推荐人手机号
//     */
//    @FormUrlEncoded
//    @POST()
//    Call<HttpEntity> updateRecommendMobile(@Url String url, @Field("recommend") String recommend);
//
//    /**
//     * 会员查询
//     */
//    @POST()
//    Call<MemberIsEntity> isMember(@Url String url);
//
//    /**
//     * 会员入会
//     */
//    @POST()
//    Call<HttpEntity> memberSign(@Url String url);

}
