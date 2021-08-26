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

            <el-form-item>
              <el-button type="primary" @click="addSubmit('admin')">立即创建</el-button>
              <el-button @click="resetForm('admin')" type="warning">重置</el-button>
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
  name: "AddAdmin",
  data(){
    return {
      msg: "管理员添加",
      admins: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ]
      },
    }
  },
  created() {

  },
  methods:{
    addSubmit(formName) {   //提交表单触发的方法

      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');

          //添加数据
          instance.post("/admin/add",this.admins).then((res)=>{
            //切换到查所有组件
            this.$router.push({name:"ShowAdmin"});
          });

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {   //表单验证 重置表单
      this.$refs[formName].resetFields();
    }
  }
}

</script>


