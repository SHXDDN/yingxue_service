<template>

  <div >
    <h1 align="center"><strong>{{ msg }}</strong></h1>
    <div >


      <el-row>
        <el-col :span="10" :offset="6">

          <!--     表单渲染数据     -->
          <el-form ref="admin" :rules="rules" :model="admins" label-width="80px">

            <el-form-item label="用户名" prop="username">
              <el-input v-model="admins.username"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input v-model="admins.password"></el-input>
            </el-form-item>

            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="admins.status" >
                <el-radio label="1" border >正常</el-radio>
                <el-radio label="0" border >冻结</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="updateSubmits('admin')">立即修改</el-button>
              <router-link :to="{name:'ShowAdmin'}" ><el-button type="danger">取消</el-button></router-link>
            </el-form-item>

          </el-form>

        </el-col>
      </el-row>
    </div>
  </div >


</template>

<script>

//import axios from 'axios'
import instance from '../../util/request'

export default {
  name: "UpdateAdmin",
  data(){
    return {
      msg: "管理员修改",
      admins: {
        username: '',
        password: '',
        status: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      },
    }
  },
  methods:{
    updateSubmits(){
      instance.post("/admin/update",this.admins).then((res)=>{

        //切换到查所有组件
        this.$router.push({name:"ShowAdmin"});
      });
    }
  },
  created() {

    //获取用户修改的id
    let id = this.$route.params.id;
    console.log("修改的id"+id);

    //根据id查询用户信息
    instance.get("/admin/queryById?id="+id).then((res)=>{

      this.admins=res.data;
    });
  }

}

</script>


