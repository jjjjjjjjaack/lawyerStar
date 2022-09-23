package com.qbo.lawyerstar.app.module.business;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.business.wap.BusinessWapAct;
import com.qbo.lawyerstar.app.module.contract.library.list.ContractLibListAct;
import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;
import com.qbo.lawyerstar.app.module.mine.login.selecttype.UserSelectTypeAct;
import com.qbo.lawyerstar.app.module.mine.order.list.all.AllOrderListAct;
import com.qbo.lawyerstar.app.module.mine.order.list.comm.base.OrderListCommAct;
import com.qbo.lawyerstar.app.module.mine.setting.SettingAct;
import com.qbo.lawyerstar.app.module.mine.suggest.SuggestUploadAct;
import com.qbo.lawyerstar.app.module.popup.PopupTipWithBtnView;
import com.qbo.lawyerstar.app.net.REQ_Factory;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.SpanManager;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;

public class LawBusinessUtils {
    //合同文库
    public final static int FUNCTION_1_HTWK = 0;
    //代写文书
    public final static int FUNCTION_2_DXWS = 1;

    //法律咨询
    public final static int FUNCTION_3_FLZX = 2;

    //律师函
    public final static int FUNCTION_4_LSH = 3;
    //合同定制
    public final static int FUNCTION_5_HTDZ = 4;
    //合同审核
    public final static int FUNCTION_6_HTSH = 5;
    //非诉催告
    public final static int FUNCTION_7_FSCG = 6;
    //仲裁诉讼
    public final static int FUNCTION_8_ZCSS = 7;
    //诉讼费计算
    public final static int FUNCTION_9_SSFJS = 8;
    //违约金计算
    public final static int FUNCTION_10_WYJJS = 9;
    //工伤赔偿
    public final static int FUNCTION_11_GSPC = 10;
    //电子签章
    public final static int FUNCTION_12_DZQZ = 11;
    //法务中心
    public final static int FUNCTION_13_FWZX = 12;
    //在线咨询
    public final static int FUNCTION_14_ZXZX = 13;
    //AI法务
    public final static int FUNCTION_15_AIFW = 14;
    //合同下载
    public final static int FUNCTION_16_HTXZ = 15;
    //案件委托
    public static int FUNCTION_17_AJWT = 16;
    //我的订单
    public final static int FUNCTION_18_WDDD = 17;
    //投诉建议
    public final static int FUNCTION_19_TSJY = 18;
    //设置
    public final static int FUNCTION_20_SZ = 19;

    public final static int FUNCTION_20_DXWS_ORDER = 20;
    public final static int FUNCTION_21_FLZX_ORDER = 21;
    public final static int FUNCTION_22_LSH_ORDER = 22;
    public final static int FUNCTION_23_HTDZ_ORDER = 23;
    public final static int FUNCTION_24_HTSH_ORDER = 24;
    public final static int FUNCTION_25_FSCG_ORDER = 25;
    public final static int FUNCTION_26_ZCSS_ORDER = 26;


    public static void jumpAction(Context context, int functionType, String extraJson) {
        Intent intent = null;
        switch (functionType) {
            case FUNCTION_1_HTWK:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                intent = new Intent(context, ContractLibListAct.class);
                context.startActivity(intent);
                break;
            case FUNCTION_2_DXWS:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "ghostwriting");
                break;
            case FUNCTION_3_FLZX:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "legal_advice");
                break;
            case FUNCTION_4_LSH:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "lawyer_letter");
                break;
            case FUNCTION_5_HTDZ:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "contract_customization");
                break;
            case FUNCTION_6_HTSH:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "contract_review");
                break;
            case FUNCTION_7_FSCG:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "non_appeal");
            case FUNCTION_8_ZCSS:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                BusinessWapAct.openAct(context, "arbitrate_litigate");
                break;
            case FUNCTION_9_SSFJS:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "legal_fee");
                break;
            case FUNCTION_10_WYJJS:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "penal_sum");
                break;
            case FUNCTION_11_GSPC:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "industry_payfor");
                break;
            case FUNCTION_12_DZQZ:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsCompany(context)) {
                    T.showShort(context, "企业认证成功后才能使用此功能");
                    return;
                }
                break;
            case FUNCTION_13_FWZX:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "legal_friends");
                break;
            case FUNCTION_14_ZXZX:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "service");
                break;
            case FUNCTION_15_AIFW:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context, "ar_service");
                break;
            case FUNCTION_16_HTXZ:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "contract_documents");
                break;
            case FUNCTION_18_WDDD:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                intent = new Intent(context, AllOrderListAct.class);
                context.startActivity(intent);
                break;
            case FUNCTION_19_TSJY:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                intent = new Intent(context, SuggestUploadAct.class);
                context.startActivity(intent);
                break;
            case FUNCTION_20_SZ:
                intent = new Intent(context, SettingAct.class);
                context.startActivity(intent);
                break;

            case FUNCTION_20_DXWS_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "ghostwriting");
                break;
            case FUNCTION_21_FLZX_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "legal_advice");
                break;
            case FUNCTION_22_LSH_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "lawyer_letter");
                break;
            case FUNCTION_23_HTDZ_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "contract_customization");
                break;
            case FUNCTION_24_HTSH_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "contract_review");
                break;
            case FUNCTION_25_FSCG_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "non_appeal");
                break;
            case FUNCTION_26_ZCSS_ORDER:
                try {
                    FTokenUtils.getToken(context, true);
                } catch (NeedLoginException e) {
                    return;
                }
                if (!checkIsRz(context, true)) {
                    return;
                }
                OrderListCommAct.openAct(context, "arbitrate_litigate");
                break;
        }
    }

    //判断用户是否认证
    public static boolean checkIsRz(Context context, boolean needJump) {
        FUserInfoBean userInfoBean = FCacheUtils.getUserInfo(context);
        if (userInfoBean == null) {
            return false;
        }
        if (!ToolUtils.String2Boolean(userInfoBean.is_rz)) {
            if (needJump) {
                Intent intent = new Intent(context, UserSelectTypeAct.class);
                context.startActivity(intent);
            }
            return false;
        }
        return true;
    }

    //判断用户是否认证
    public static boolean checkIsCompany(Context context) {
        FUserInfoBean userInfoBean = FCacheUtils.getUserInfo(context);
        if (userInfoBean == null) {
            return false;
        }
        if ("1".equals(userInfoBean.user_type)) {
            return true;
        }
        return false;
    }

    //判断用户是否认证
    public static boolean checkIsVip(Context context) {
        FUserInfoBean userInfoBean = FCacheUtils.getUserInfo(context);
        if (userInfoBean == null) {
            return false;
        }
        return userInfoBean.isVip();
    }

    public static void showVipTipView(Context context, View parent) {
        REQ_Factory.GET_ABOUT_US_INFO_REQ req = new REQ_Factory.GET_ABOUT_US_INFO_REQ();
        BasePresent.doStaticCommRequest(context, req, true, true, new BasePresent.DoCommRequestInterface<BaseResponse, AboutUsBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public AboutUsBean doMap(BaseResponse baseResponse) {
                AboutUsBean usBean = AboutUsBean.fromJSONAuto(baseResponse.datas, AboutUsBean.class);
                return usBean;
            }

            @Override
            public void onSuccess(AboutUsBean bean) throws Exception {
                String str = "如需购买会员,请联系我司业务人员\n请拨打\n";
                int pos1 = str.length();
                str += bean.getConsumer_hotline();
                PopupTipWithBtnView.showPopTipView(context, "开通会员",
                        SpanManager.getInstance(str)
                                .setForegroundColorSpan(pos1, str.length(),
                                        context.getResources().getColor(R.color.main_color)).toBuild(),
                        new PopupTipWithBtnView.PopupTipWithBtnInterface() {
                            @Override
                            public void okClick() {
                                ToolUtils.callPhone(context, bean.getConsumer_hotline());
                            }

                            @Override
                            public void cancleClick() {

                            }

                            @Override
                            public void onDisimss() {

                            }
                        }, parent);

            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }


}
