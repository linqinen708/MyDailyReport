package com.qyd.mydailyreport.constants

/**
 * Created by Ian on 2018/6/29.
 */
object MyExtra {
    /**注册页面的账号数据*/
    const val ACCOUNT:String = "ACCOUNT"
    /**注册页面的密码数据*/
    const val PASSWORD:String = "PASSWORD"
    /**租约的数据*/
    const val CONTRACT_DATA:String = "contract_data"
    /**租约的id*/
    const val CONTRACT_ID:String = "contract_id"
    /**续租*/
    const val RENEW_LEASE:String = "lease_xuzu"
    /**编辑续租*/
    const val EDITE_RENEW_LEASE:String = "lease_edit"
    /**定金租约*/
    const val DEPOSIT_LEASE:String = "lease_dingjin"
    /**建租*/
    const val LEASE_DETAIL:String = "lease_detail"
    /**房源详情的houseId*/
    const val HOUSE_ID:String = "house_id"
    /**家具清单*/
    const val FURNITURE_LIST:String = "FURNITURE_LISST"
    /**公共家具清单*/
    const val PUBLIC_FURNITURE_LIST:String = "PUBLIC_FURNITURE_LIST"
    /**公共家具清单的title，因为家具界面和公共家具界面是同一个，所以进入公共家具界面，toolbar应该显示公共家具清单*/
    const val PUBLIC_FURNITURE_TITLE:String = "PUBLIC_FURNITURE_TITLE"
}