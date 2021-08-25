<template>

  <div >
    <h1 align="center"><strong>{{ msg }}</strong></h1>
    <div ><br>

      <!--   表单数据展示   -->
      <el-table border style="width: 100%" :row-class-name="tableRowClassName" :data="feedbacks.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase()))">

        <!--    复选框列    -->
        <el-table-column type="selection" width="55"></el-table-column>
        <!--    数据列    -->
        <el-table-column prop="id" label="用户id" width="180" sortable></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="content" label="内容"></el-table-column>
        <el-table-column prop="userId" label="用户id" sortable></el-table-column>
        <el-table-column prop="createTime" label="添加时间" sortable></el-table-column>

        <!--    操作列    -->
        <el-table-column >
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="deleteFeedback(scope.row.id)">删除</el-button>
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
  name: "ShowFeedback",
  data(){  //数据
    return {
      msg:"反馈管理",
      feedbacks:[],  //用户数据展示
      page:1,  //指定当前页
      total:0, //指定总条数
      pageSizes:[2,3,5,10,15,20],  //配置每页展示数据数选项
      pageSize:2,   //选择每页展示条数  必须在pageSizes中
      search:"",  //搜索数据
    }
  },
  created() {  //初始化页面触发函数

    //查询所有数据
    this.queryFeedback(this.page,this.pageSize);
  },
  methods:{  //自定函数
    tableRowClassName({row, rowIndex}) {  //加载表格渲染斑马线样式
      if(rowIndex%2==0)return 'warning-row';
      if(rowIndex%2!=0)return 'success-row';
    },
    pageSizeChange(size) {  //修改每页展示条数触发  参数：每页条数
      console.log(`每页 ${size} 条` );
      //查询所有数据
      this.queryFeedback(1,size);
      //从新为每页展示条数赋值
      this.pageSize=size;
    },
    currentChange(page) {  //修改当前页触发  参数：当前页
      console.log(`修改----当前页: ${page}`);
      console.log(`修改----每页展示条数: ${this.pageSize}`);
      //查询所有数据
      this.queryFeedback(page,this.pageSize);
    },
    queryFeedback(page,pageSize){  //查询所有数据的方法


      instance.post("/feedback/queryAllPage?page="+page+"&pageSize="+pageSize).then((res)=>{

        //数据赋值
        this.feedbacks=res.data.rows;
        this.page=res.data.page;
        this.total=res.data.total;

        //console.log(res);
      })
    },
    deleteFeedback(id){
      /**1.删除确认提示框*/
      if(window.confirm("您确定要删除该数据吗?")){
        /**2.发送请求删除数据*/
        instance.post("/feedback/delete",{"id":id}).then((res)=>{
          /**3.查询所有数据*/
          this.queryFeedback(1,this.pageSize);

          /**4.删除提示框*/
          this.$message({
            message: res.data.message, //提示框提示的信息
            type: 'success',  //提示框颜色样式
          });
        });
      }
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


