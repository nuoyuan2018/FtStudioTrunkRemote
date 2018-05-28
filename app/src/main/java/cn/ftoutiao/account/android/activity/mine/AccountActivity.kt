package cn.ftoutiao.account.android.activity.mine

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import cn.ftoutiao.account.android.BuildConfig
import cn.ftoutiao.account.android.R
import cn.ftoutiao.account.android.activity.mine.view.CircleImageView
import cn.ftoutiao.account.android.base.BaseActivity
import cn.ftoutiao.account.android.base.EventBusHelper
import cn.ftoutiao.account.android.constants.ConstanPool
import cn.ftoutiao.account.android.constants.DataContans
import cn.ftoutiao.account.android.constants.PermissionConstant
import cn.ftoutiao.account.android.model.evenbus.LoginLose
import cn.ftoutiao.account.android.model.evenbus.LoginOut
import cn.ftoutiao.account.android.utils.AccountManager
import cn.ftoutiao.account.android.utils.FileUtil
import cn.ftoutiao.account.android.utils.FileUtil.getRealFilePathFromUri
import cn.ftoutiao.account.android.utils.TopBar
import cn.ftoutiao.account.android.widget.DialogUtils
import com.acmenxd.toaster.Toaster
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_accout.*
import kotlinx.android.synthetic.main.layout_popupwindow.view.*
import org.greenrobot.eventbus.Subscribe
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


/**
 * 账号管理
 */
class AccountActivity : BaseActivity(), AccountContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks {


    //请求相机
    private val REQUEST_CAPTURE = 100
    //请求相册
    private val REQUEST_PICK = 101
    //请求截图
    private val REQUEST_CROP_PHOTO = 102
    //请求访问外部存储
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 103
    //请求写入外部存储
    private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104

    private val MODIFY_REQUEST_CODE = 105

    private var mPresenter: AccountPresenter? = null

    //调用照相机返回图片文件
    private var tempFile: File? = null
    // 1: qq, 2: weixin
    private var type: Int = 0
    private var bitMap: Bitmap? = null


    override fun initLayout() {
        setContentView(R.layout.activity_accout)
    }

    override fun initValue() {
        topbar.setTextValue(getString(R.string.uc_account_manager), TopBar.MID)
        mPresenter = AccountPresenter(this)
        addPresenters(mPresenter)
        btn_exit.isEnabled = true
        undateUserInfo()
    }

    var circleImg: CircleImageView? = null

    override fun initView() {
        super.initView()
        circleImg = getView(R.id.circle_img)

    }

    override fun initListener() {
        topbar.setonTopbarLeftLayoutListener { finish() }
        btn_exit.setOnClickListener(this)
        circleImg!!.setOnClickListener(this)
        tv_callback.setOnClickListener(this)
        rl_deal_record.setOnClickListener(this)
    }

    private fun undateUserInfo() {
        if (DataContans.isLogin) {
            if (DataContans.isAccountLogin()) {
                tv_callback.visibility = View.VISIBLE
            } else {
                tv_callback.visibility = View.GONE
            }
            val userEntity = DataContans.userEntity.data;

            Glide.with(context)
                    .load(userEntity.avatarurl)
                    .into(circle_img)
            tv_deal_count.setText(userEntity.nickname)
        }
    }


    override fun onClick(p0: View?) {
        viewTimeClickable(p0, 1000)
        when (p0!!.id) {
            btn_exit.id -> {

                DialogUtils.exit(this@AccountActivity,
                        DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.dismiss()
                            mPresenter!!.loginOut()
                        })

            }
            circle_img.id -> {
                type = 1
                uploadHeadImage()
            }
            tv_callback.id -> {
                startActivity(ModifyPwdActivity::class.java)
            }

            rl_deal_record.id -> {

            }
        }

    }

    override fun loginOutSuccess() {
        /**
         * 通知刷新
         */
        Toaster.show(getString(R.string.exit_success))
        EventBusHelper.post(LoginOut())
        AccountManager.getInstance().setAccount(context, null)
        DataContans.setUserEntity(null)
        Handler().postDelayed(Runnable {
            finish()
        }, 500)

    }

    override fun loginOutFailed(msg: String?) {
        Toaster.show(msg)
        finish()

    }

    /**
     * 上传头像
     */
    private fun uploadHeadImage() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null)
        view.btn_camera
        val btnCarema = view.btn_camera as TextView
        val btnPhoto = view.btn_photo as TextView
        val btnCancel = view.btn_cancel as TextView
        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.animationStyle = R.style.SlipDialogAnimation
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))

        popupWindow.isOutsideTouchable = true
        val parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0)
        //popupWindow在弹窗的时候背景半透明
        val params = window.attributes
        params.alpha = 0.5f
        window.attributes = params
        popupWindow.setOnDismissListener {
            params.alpha = 1.0f
            window.attributes = params
        }

        btnCarema.setOnClickListener {
            //权限判断
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this@AccountActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            } else {
                //跳转到调用系统相机
                gotoCamera()
            }
            popupWindow.dismiss()
        }
        btnPhoto.setOnClickListener {
            //权限判断
            if (ContextCompat.checkSelfPermission(this@AccountActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请READ_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this@AccountActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_EXTERNAL_STORAGE_REQUEST_CODE)
            } else {
                //跳转到相册
                gotoPhoto()
            }
            popupWindow.dismiss()
        }
        btnCancel.setOnClickListener { popupWindow.dismiss() }
    }

    /**
     * 跳转到相册
     */
    private fun gotoPhoto() {
        Log.d("evan", "*****************打开图库********************")
        //跳转到调用系统图库
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, getString(R.string.uc_please_select_photo)), REQUEST_PICK)
    }

    private fun gotoCamera() {
        Log.d("evan", "*****************打开相机********************")
        //创建拍照存储的图片文件
        tempFile = File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().path + "/image/"), System.currentTimeMillis().toString() + ".jpg")
        if (pub.devrel.easypermissions.EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            openCamera()
            true
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name) + getString(R.string.uc_need_cameral), PermissionConstant.PERMISSION_REQUEST_CODE, Manifest.permission.CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        when (requestCode) {
            MODIFY_REQUEST_CODE
            -> if (resultCode == Activity.RESULT_OK) {
                var nickname = intent!!.getStringExtra(ConstanPool.BUNDLE)
                tv_deal_count.text = nickname;
            }
            REQUEST_CAPTURE //调用系统相机返回
            -> if (resultCode == Activity.RESULT_OK) {
                gotoClipActivity(Uri.fromFile(tempFile))
            }
            REQUEST_PICK  //调用系统相册返回
            -> if (resultCode == Activity.RESULT_OK) {
                val uri = intent!!.data
                gotoClipActivity(uri)
            }
            REQUEST_CROP_PHOTO  //剪切图片返回
            -> if (resultCode == Activity.RESULT_OK) {
                val uri = intent!!.data ?: return
                val cropImagePath = getRealFilePathFromUri(applicationContext, uri)
                Glide.with(this).load(cropImagePath).into(circle_img)
                mPresenter?.updateHeaderImage(File(cropImagePath))

            }
        }
    }

    /**
     * 打开截图界面
     */
    fun gotoClipActivity(uri: Uri) {
        val intent = Intent()
        intent.setClass(this@AccountActivity, ClipImageActivity::class.java)
        intent.putExtra("type", type)
        intent.data = uri
        startActivityForResult(intent, REQUEST_CROP_PHOTO)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        openCamera()
    }

    private fun openCamera() {
        //跳转到调用系统相机
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xmldialo
            intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            val contentUri = FileProvider.getUriForFile(this@AccountActivity, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        }
        startActivityForResult(intent, REQUEST_CAPTURE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    @Subscribe(priority = 10)
    fun LoginLoseResopond(lose: LoginLose) {
        AccountManager.getInstance().setAccount(this@AccountActivity, null)
        DataContans.setUserEntity(null)
        finish()
    }


}
