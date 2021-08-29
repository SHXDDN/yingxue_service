<template>

  <div >
    <h1 align="center"><strong>{{ msg }}</strong></h1>
    <div ><br>

      <!--   表单数据展示   -->
      <el-table border style="width: 100%" :row-class-name="tableRowClassName" :data="logs.filter(data => !search || data.adminName.toLowerCase().includes(search.toLowerCase()))">

        <!--    复选框列    -->
        <el-table-column type="selection" width="55"></el-table-column>
        <!--    数据列    -->
        <el-table-column prop="id" label="id" width="180" sortable></el-table-column>
        <el-table-column prop="adminName" label="管理员名"></el-table-column>
        <el-table-column prop="optionTime" label="操作时间"></el-table-column>
        <el-table-column prop="methodName" label="方法名" sortable></el-table-column>
        <el-table-column prop="status" label="操作状态" sortable></el-table-column>

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
  name: "ShowLogs",
  data(){  //数据
    return {
      msg:"日志管理",
      logs:[],  //用户数据展示
      page:1,  //指定当前页
      total:0, //指定总条数
      pageSizes:[10,15,20,30,50,100,200],  //配置每页展示数据数选项
      pageSize:10,   //选择每页展示条数  必须在pageSizes中
      search:"",  //搜索数据
    }
  },
  created() {  //初始化页面触发函数
    //查询所有数据
    this.queryLog(this.page,this.pageSize);
  },
  methods:{  //自定函数
    tableRowClassName({row, rowIndex}) {  //加载表格渲染斑马线样式
      if(rowIndex%2==0)return 'warning-row';
      if(rowIndex%2!=0)return 'success-row';
    },
    pageSizeChange(size) {  //修改每页展示条数触发  参数：每页条数
      console.log(`每页 ${size} 条` );
      //查询所有数据
      this.queryLog(1,size);
      //从新为每页展示条数赋值
      this.pageSize=size;
    },
    currentChange(page) {  //修改当前页触发  参数：当前页
      console.log(`修改----当前页: ${page}`);
      console.log(`修改----每页展示条数: ${this.pageSize}`);
      //查询所有数据
      this.queryLog(page,this.pageSize);
    },
    queryLog(page,pageSize){  //查询所有数据的方法

      instance.post("/log/queryAllPage?page="+page+"&pageSize="+pageSize).then((res)=>{

        //数据赋值
        this.logs=res.data.rows;
        this.page=res.data.page;
        this.total=res.data.total;

        //console.log(res);
      })
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


