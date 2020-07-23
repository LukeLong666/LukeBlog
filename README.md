# 博客系统开发

  

  ## 开发环境

  * IDE: IDEA , VS_CODE 
  * 编程语言: Java , Vue.js , Nuxt.js , Sql语句
  * 数据库: MySql , Redis
  * 技术栈: Spring 全家桶
  * Java版本: jdk 1.8

  ## 功能说明

  * 门户
    * 用户
      * 登录
      * 退出
      * 注册
      * 找回密码
    * 文章
      * 文章详情
      * 获取文章列表
      * 文章搜索
      * 获取推荐文章
    * 评论
      * 添加评论
      * 删除评论
      * 根据文章获取评论列表
    * 网站信息
      * 获取分类列表
      * 访问总量-每天增量
      * 获取轮播图列表
      * 获取网站标题
      * 获取SEO信息
      * 获取友情连接
  * 管理中心
    * 用户
      * 管理员账号初始化
      * 用户信息更新
      * 密码重置
      * 获取用户列表
      * 删除用户
      * 退出登录
    * 文章内容
      * 发布文章
      * 更新文章
      * 删除文章
      * 获取文章列表
      * 预览文章
      * 文章置顶
      * 文章搜索
    * 轮播图设置
      * 是否开启轮播图
      * 添加轮播图内容
      * 删除轮播图内容
      * 获取轮播图
      * 获取轮播图列表
    * 文章分类
      * 添加文章分类
      * 修改文章分类
      * 删除文章分类
      * 获取分类列表
    * 友情链接
      * 添加友情链接
      * 修改友情链接
      * 删除友情链接
      * 获取友情链接列表
    * 评论内容
      * 评论置顶
      * 删除评论
      * 获取评论列表
    * 设置
      * SEO信息
        * 修改网站SEO信息
        * 获取网站SEO信息
      * 网站标题
        * 获取网站标题
        * 设置网站标题

  ## [数据库设计](/Database.md)

  ## [数据库语句](/luke_blog.sql)

  ## API接口设计

  ### 用户user
    * 初始化管理员账号-init-admin
    * 注册join-in
    * 登录sign-up
    * 获取人类验证码captcha
    * 发送邮件email
    * 修改密码password
    * 获取作者信息user-info
    * 修改用户信息user-info
    * 获取用户列表
    * 删除用户
    * 重置用户密码
  ### 管理admin
    * 分类category
      * 添加分类
      * 删除分类
      * 修改分类
      * 获取分类列表
    * 图片image
      * 图片删除
      * 获取图片列表list
      * 图片上传
    * 文章article
      * 发表文章
      * 获取文章详情
      * 文章列表list
      * 修改文章
      * 文章置顶 top
      * 删除文章
    * 网站信息web-size
      * 获取网站标题title
      * 修改网站标题
      * 获取seo信息
      * 修改网站的seo信息
      * 获取统计信息view-count
    * 轮播图looper
      * 添加/修改轮播图
      * 删除轮播图
      * 获取轮播图列表list
      * 是否开启轮播图enable
    * 友情链接link
      * 添加友情链接
      * 更新友情链接
      * 删除友情链接
      * 获取友情链接列表list
    * 评论comment
      * 评论置顶top
      * 获取评论列表
      * 获取评论
      * 删除评论
### 门户portal
  * 文章article
    * 获取文章列表
    * 根据分类获取内容category
    * 获取文章详情
    * 获取推荐文章
  * 评论comment
    * 添加评论
    * 删除评论
    * 获取评论列表
  * 网站web-size
    * 获取分类categories
    * 获取网站标题title
    * 获取访问量数据view-count
    * 获取网站seo信息seo
    * 获取轮播图列表looper-list
    * 获取友情链接列表links
  * 文章搜索search

## 功能实现

### 用户注册流程

#### [图灵验证码](https://github.com/whvcse/EasyCaptcha)

   ```java
   @RequestMapping("/captcha")
       public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
           // 设置请求头为输出图片类型
           response.setContentType("image/gif");
           response.setHeader("Pragma", "No-cache");
           response.setHeader("Cache-Control", "no-cache");
           response.setDateHeader("Expires", 0);
   
           // 三个参数分别为宽、高、位数
           SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
           // 设置字体
           specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
           // 有默认字体，可以不用设置
           // 设置类型，纯数字、纯字母、字母数字混合
           specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
   
           // 验证码存入session
           request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
   
           // 输出图片流
           specCaptcha.out(response.getOutputStream());
       }
   ```

   ##### 图灵验证码的使用

   * 前端请求图灵验证码
   * 后台生成图灵验证码,并且保存在session里
   * 用户提交注册
   * 从session中拿出存储的验证码内容跟用户提交的比对
   * 返回结果

   ##### 前后端分离

   * 前端生成随机数以参数的形式添加到图灵验证码的url上
   * 后台生成验证码,返回结果,并且保存再redis里key-value,并设置有效期
   * 用户提交注册信息
   * 根据key从redis里拿出内容,与用户提交的比对
   * 如果正确,删除redis记录,如果不正确返回前端

#### [邮箱验证]()

