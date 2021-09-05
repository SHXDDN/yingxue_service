import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    { name: "Login", path: "/login", component: () => import('../views/login/Login')},
    {
      path: "/",
      component: () => import('../views/Main'),
      redirect: '/mainView',
      children: [
        {name: 'MainView',path: 'mainView',component: () => import('../views/main/MainView')},
        {name: 'ShowUser',path: 'showUser',component: ()=> import("../views/user/ShowUser")},
        {name: 'AddUser',path: 'addUser',component: ()=> import("../views/user/AddUser")},
        {name: 'UpdateUser',path: 'updateUser',component: ()=> import("../views/user/UpdateUser")},
        {name: 'ShowFeedback',path: 'feedback',component: ()=> import("../views/feedback/ShowFeedback")},

        {name: 'ShowAdmin',path: 'admin',component: ()=> import("../views/admin/ShowAdmin")},
        {name: 'AddAdmin',path: 'addAdmin',component: ()=> import("../views/admin/AddAdmin")},
        {name: 'UpdateAdmin',path: 'updateAdmin',component: ()=> import("../views/admin/UpdateAdmin")},

        {name: 'ShowCategory',path: 'category',component: ()=> import("../views/category/ShowCategory")},
        {name: 'AddCategory',path: 'addCategory',component: ()=> import("../views/category/AddCategory")},
        {name: 'UpdateCategory',path: 'updateCategory',component: ()=> import("../views/category/UpdateCategory")},

        {name: 'ShowLog',path: 'log',component: ()=> import("../views/log/ShowLog")},

        {name: 'ShowVideo',path: 'video',component: ()=> import("../views/video/ShowVideo")},
        {name: 'AddVideo',path: 'addVideo',component: ()=> import("../views/video/AddVideo")},
        {name: 'UpdateVideo',path: 'updateVideo',component: ()=> import("../views/video/UpdateVideo")},
        {name: 'SearchVideo',path: 'searchVideo',component: ()=> import("../views/video/SearchVideo")},
      ]},
    {path: "*",name:"Error404",component:()=>import("../views/error/404")}
  ]
})
