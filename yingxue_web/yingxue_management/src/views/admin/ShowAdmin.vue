<template>

  <div >
    <h1 align="center"><strong>{{ msg }}</strong></h1>
    <div ><br>

      <router-link :to="{name:'AddAdmin'}"  ><el-button type="primary">添加管理员</el-button></router-link><br><br>

      <!--   表单数据展示   -->
      <el-table border style="width: 100%" :row-class-name="tableRowClassName" :data="admins.filter(data => !search || data.username.toLowerCase().includes(search.toLowerCase()))">

        <!--    复选框列    -->
        <el-table-column type="selection" width="55"></el-table-column>
        <!--    数据列    -->
        <el-table-column prop="id" label="id" width="180" sortable></el-table-column>
        <el-table-column prop="username" label="名字"></el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-button type="warning" v-if="scope.row.status==0" @click="changeStatus(scope.row)" >冻结</el-button>
            <el-button type="success" v-else @click="changeStatus(scope.row)" >正常</el-button>
            <!--        {{aa(scope.row.status)+scope.$index}}-->
          </template>
        </el-table-column>
        <el-table-column prop="salt" label="盐" sortable></el-table-column>

        <!--    操作列    -->
        <el-table-column >
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button size="mini" type="warning" @click="updateAdmin(scope.row.id)">修改</el-button>
            <el-button size="mini" type="danger" @click="deleteAdmin(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>

      </el-table><br>

      <!--   配置分页工具栏   -->
      <div class="block" align="center">
        <el-pagination
          style="margin: 15px 0px"
          background
          :page-sizes="pageSizes"
          :page-size="pageSize"
          :current-page="page"
          prev-text="上一页"
          next-text="下一页"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="pageSizeChange"
          @current-change="currentChange"
        >
        </el-pagination>
      </div>

    </div>
  </div >

</template>

<script>

//import axios from 'axios'
import instance from '../../util/request'  //导入asiox 的实例

export default {
  name: "ShowAdmin",
  data(){  //数据
    return {
      msg:"管理员管理",
      admins:[],  //用户数据展示
      page:1,  //指定当前页
      total:0, //指定总条数
      pageSizes:[2,3,5,6,10,15,200],  //配置每页展示数据数选项
      pageSize:2,   //选择每页展示条数  必须在pageSizes中
      search:"",  //搜索数据
    }
  },
  created() {  //初始化页面触发函数
    //查询所有数据
    this.queryAdmin(this.page,this.pageSize);
  },
  methods:{  //自定函数
    tableRowClassName({row, rowIndex}) {  //加载表格渲染斑马线样式
      if(rowIndex%2==0)return 'warning-row';
      if(rowIndex%2!=0)return 'success-row';
    },
    pageSizeChange(size) {  //修改每页展示条数触发  参数：每页条数
      console.log(`每页 ${size} 条` );
      //查询所有数据
      this.queryAdmin(1,size);
      //从新为每页展示条数赋值
      this.pageSize=size;
    },
    currentChange(page) {  //修改当前页触发  参数：当前页
      console.log(`修改----当前页: ${page}`);
      console.log(`修改----每页展示条数: ${this.pageSize}`);
      //查询所有数据
      this.queryAdmin(page,this.pageSize);
    },
    queryAdmin(page,pageSize){  //查询所有数据的方法

      instance.post("/admin/queryAllPage?page="+page+"&pageSize="+pageSize).then((res)=>{

        //数据赋值
        this.admins=res.data.rows;
        this.page=res.data.page;
        this.total=res.data.total;

        //console.log(res);
      })
    },
    changeStatus(row){

      /**1.判断用户状态*/
      if(row.status=="1"){
        /**2.发送修改请求*/
        instance.post("/admin/update",{"id":row.id,"status":"0"}).then((res)=>{
          /**3.查询所有数据*/
          this.queryAdmin(this.page,this.pageSize);

          /**4.修改提示框*/
          this.$message({
            message: res.data.message, //提示框提示的信息
            type: 'success',  //提示框颜色样式
          });
        });
      }else{
        /**2.发送修改请求*/
        instance.post("/admin/update",{"id":row.id,"status":"1"}).then((res)=>{
          /**3.查询所有数据*/
          this.queryAdmin(this.page,this.pageSize);

          /**4.修改提示框*/
          this.$message({
            message: res.data.message, //提示框提示的信息
            type: 'success',  //提示框颜色样式
          });
        });
      }
    },
    deleteAdmin(id){
      /**1.删除确认提示框*/
      if(window.confirm("您确定要删除该数据吗?")){
        /**2.发送请求删除数据*/
        instance.post("/admin/delete",{"id":id}).then((res)=>{
          /**3.查询所有数据*/
          this.queryAdmin(1,this.pageSize);

          /**4.删除提示框*/
          this.$message({
            message: res.data.message, //提示框提示的信息
            type: 'success',  //提示框颜色样式
          });
        });
      }
    },
    updateAdmin(id){
      this.$router.push({name:"UpdateAdmin",params:{id:id}});
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  /*display: inline-block;*/
  margin: 0 10px;
}
a {
  color: #42b983;
}
.aa{
  background-color: #ccaadd
}
.bb{
  background-color: aquamarine;
}
</style>


