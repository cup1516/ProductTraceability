<template>
  <div class="app-container">
    <div class="filter-container">
       <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          icon="el-icon-search"
          @click="handleSearchList()"
          size="mini">搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
           icon="el-icon-refresh"
          size="mini">重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="tempProduction" size="small" label-width="140px">

          <el-form-item label="作物种类:">
          <el-select  v-model="tempProduction.crops" placeholder="请选择农作物种类"  style="width:170px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
          </el-form-item>

          <el-form-item label="开始时间：">
              <el-date-picker
                v-model="tempProduction.stime"
                type="date"
                style="width:170px"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
                   <el-form-item label="截止时间：" label-width="140px">
              <el-date-picker
                v-model="tempProduction.etime"
                type="date"
                 style="width:170px"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
        <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button
            @click="getList"
            style="float:right"
              size="mini" 
           icon="el-icon-refresh"
          >刷新</el-button>
    </el-card>
    </div>
    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"

      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span>{{scope.$index+1 }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="id" label="编号" width="270"></el-table-column>
      <el-table-column align="center" prop="crops" label="农作物种类" width="100"></el-table-column>
      <el-table-column align="center" prop="amount" label="数量(kg)" width="200"></el-table-column>
      <el-table-column align="center" prop="date" label="创建时间" width="200"></el-table-column>
      <el-table-column align="center" prop="date" label="修改时间" width="200"></el-table-column>
      <el-table-column align="center" label="操作" width="200" >
        <template slot-scope="scope">
          <el-button
            type="info"
           icon="el-icon-tickets"
            @click="getWork(scope.$index)"
           size="mini" 
          >查看工作单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

  </div>
</template>
<script>

import { list ,find} from "@/api/manor/production/production";
import { getCrops } from "@/api/manor/order/add";



export default {
  data() {
    return {
      total: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: ""
      },
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "添加",
        find:"查找"
      },
      tempProduction:{
        id:"",
        crops:"",
        date:"",
        amount:"",
        type:"",
        stime:"",
        etime:""
      },
      crops:[]
    };
  },
  created() {
    this.getList();
   this.getCrops();

  },
  methods: {

    getList() {
      this.listLoading = true;

      list(this.listQuery).then(response => {
        
        const data = response.data
        this.listLoading = false;
        this.list = data.content;
        this.total = data.totalElements;
        this.pageNum = data.number;
        this.pageRow = data.size;
        }
      );
    },


    getCrops() {
      getCrops().then(response => {
        const data = response.data
        this.crops = data;
        }
      );
    },
    

    handleResetSearch(){
      this.tempProduction.crops ="",
      this.tempProduction.etime="",
      this.tempProduction.stime= ""
    },
  
  getWork($index){
      this.$router.push({path:'/detail/finalWorkDetail',
       query:{id:this.list[$index].id}
     });
   },

    msgList(params) {
      return new Promise((resolve, reject) => {
        axios
          .post(api.MSG_TOP_TEN, { params })
          .then(
            response => {
              resolve(response.data);
            },
            err => {
              resolve(defaultValue.msgList);
            }
          )
          .catch(error => {
            resolve(defaultValue.msgList);
          });
      });
    },
    menuList(params) {
      return new Promise((resolve, reject) => {
        axios
          .get(api.SYS_MENU_LIST, { params })
          .then(
            response => {
              resolve(response.data);
            },
            err => {
              resolve(defaultValue.menuList);
            }
          )
          .catch(error => {
            resolve(defaultValue.menuList);
          });
      });
    },
    handleSizeChange(val) {
      //改变每页数量
      this.pageRow = val;
      this.listQuery.pageRow = val;
      this.getList();
      //this.handleFilter();
    },
    handleCurrentChange(val) {
      //改变页码
      this.pageNum = val;
      this.listQuery.pageNum = val;
      this.getList();
    },
    
    getIndex($index) {
      //表格序号
      return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1;
    },
 

    handleSearchList(){
      this.listLoading = true;
      find(
        {
          "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "crops":this.tempProduction.crops,
          "etime":this.tempProduction.etime,
          "stime":this.tempProduction.stime,
        }
      ).then(response =>{
        const data = response.data
        this.listLoading = false;
        this.list = data.content;
        this.total = data.total;
        this.pageNum = data.current;
        this.pageRow = data.size;
      })
    },
 
 
  }
};
</script>
