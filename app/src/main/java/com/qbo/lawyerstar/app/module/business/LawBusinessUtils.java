package com.qbo.lawyerstar.app.module.business;

import android.content.Context;
import android.content.Intent;

import com.qbo.lawyerstar.app.module.business.wap.BusinessWapAct;
import com.qbo.lawyerstar.app.module.contract.library.list.ContractLibListAct;
import com.qbo.lawyerstar.app.module.mine.setting.SettingAct;
import com.qbo.lawyerstar.app.module.mine.suggest.SuggestUploadAct;

import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.util.FTokenUtils;

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
    public static int FUNCTION_12_DZQZ = 11;
    //法务中心
    public static int FUNCTION_13_FWZX = 12;
    //在线咨询
    public static int FUNCTION_14_ZXZX = 13;
    //AI法务
    public static int FUNCTION_15_AIFW = 14;
    //合同下载
    public static int FUNCTION_16_HTXZ = 15;
    //案件委托
    public static int FUNCTION_17_AJWT = 16;
    //我的订单
    public static int FUNCTION_18_WDDD = 17;
    //投诉建议
    public final static int FUNCTION_19_TSJY = 18;
    //设置
    public final static int FUNCTION_20_SZ = 19;

    public static void jumpAction(Context context, int functionType, String extraJson) {
        Intent intent = null;
        switch (functionType) {
            case FUNCTION_1_HTWK:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                intent = new Intent(context, ContractLibListAct.class);
                context.startActivity(intent);
                break;
            case FUNCTION_2_DXWS:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"ghostwriting");
                break;
            case FUNCTION_3_FLZX:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"legal_advice");
                break;
            case FUNCTION_4_LSH:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"lawyer_letter");
                break;
            case FUNCTION_5_HTDZ:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"contract_customization");
                break;
            case FUNCTION_6_HTSH:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"contract_review");
                break;
            case FUNCTION_7_FSCG:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"non_appeal");
            case FUNCTION_8_ZCSS:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"arbitrate_litigate");
                break;
            case FUNCTION_9_SSFJS:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"legal_fee");
                break;
            case FUNCTION_10_WYJJS:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"penal_sum");
                break;
            case FUNCTION_11_GSPC:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                BusinessWapAct.openAct(context,"industry_payfor");
                break;
            case FUNCTION_20_SZ:
                intent = new Intent(context, SettingAct.class);
                context.startActivity(intent);
                break;
            case FUNCTION_19_TSJY:
                try {
                    FTokenUtils.getToken(context,true);
                } catch (NeedLoginException e) {
                    return;
                }
                intent = new Intent(context, SuggestUploadAct.class);
                context.startActivity(intent);
                break;
        }
    }

}
