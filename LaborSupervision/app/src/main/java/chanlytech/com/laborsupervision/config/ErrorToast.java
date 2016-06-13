package chanlytech.com.laborsupervision.config;

import android.content.Context;
import android.content.Intent;

import com.arialyy.frame.util.show.T;

/**
 * Created by Lyy on 2015/7/27.
 */
public class ErrorToast {


    public static void showError(Context context,int index){
        switch (index){
            case ErrorCode.ERROR_REQUEST:
                T.showLong(context, ErrorMsg.ERROR_REQUEST);
                break;
            case ErrorCode.ERROR_PARAM:
                T.showLong(context, ErrorMsg.ERROR_PARAM);
                break;
            case ErrorCode.INTERNET_ERROR:
                T.showLong(context, ErrorMsg.INTERNET_ERROR);
                break;
            case ErrorCode.SEND_FAILED:
                T.showLong(context, ErrorMsg.SEND_FAILED);
                break;
            case ErrorCode.USER_IS_REGISTER:
                T.showLong(context, ErrorMsg.USER_IS_REGISTER);
                break;
            case ErrorCode.PASSWORD_ERROR:
                T.showLong(context, ErrorMsg.PASSWORD_ERROR);
                break;
            case ErrorCode.PHONE_NOT_REGISTER:
                T.showLong(context, ErrorMsg.PHONE_NOT_REGISTER);
                break;
            case ErrorCode.CODE_ERROR:
                T.showLong(context, ErrorMsg.CODE_ERROR);
                break;
            case ErrorCode.CODE_EXPIRED:
                T.showLong(context, ErrorMsg.CODE_EXPIRED);
                break;
            case ErrorCode.REGISTER_FAILED:
                T.showLong(context, ErrorMsg.REGISTER_FAILED);
                break;
            case ErrorCode.UPLOAD_AVATAR_FAILED:
                T.showLong(context, ErrorMsg.UPLOAD_AVATAR_FAILED);
                break;
            case ErrorCode.MODIFY_PASSWORD_FAILED:
                T.showLong(context, ErrorMsg.MODIFY_PASSWORD_FAILED);
                break;
            case ErrorCode.ORDER_ID_ERROR:
                T.showLong(context, ErrorMsg.ORDER_ID_ERROR);
                break;
            case ErrorCode.CONTINUOUS_RECODE:
                T.showLong(context, ErrorMsg.CONTINUOUS_RECODE);
                break;
            case ErrorCode.COUPON_CODE_ERROR:
                T.showLong(context, ErrorMsg.COUPON_CODE_ERROR);
                break;
            case ErrorCode.COUPON_CODE_FAILED:
                T.showLong(context, ErrorMsg.COUPON_CODE_FAILED);
                break;
            case ErrorCode.USER_EXPIRED:
                T.showLong(context, ErrorMsg.USER_EXPIRED);
//                Intent mIntent=new Intent(context,CustomizedPackagesActivity.class);
//                context.startActivity(mIntent);
                break;
            case ErrorCode.UPGRADE_IS_SUBMITTED:
                T.showLong(context, ErrorMsg.UPGRADE_IS_SUBMITTED);
                break;
            case ErrorCode.SUBMIT_FAIL:
                T.showLong(context,ErrorMsg.SUBMIT_FAIL);
                break;
            case ErrorCode.NO_FIND:
                T.showLong(context,ErrorMsg.NO_FIND);
                break;




        }
    }
}
