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
          @click="find()"
          size="mini"
          icon="el-icon-search"
          >查询
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="mini"
           icon="el-icon-refresh"
         >重置
        </el-button>
      </div>

      <div style="margin-top: 15px">
        <el-form :inline="true"  size="small" label-width="140px" :model="tempFind">
          <el-form-item label="输入搜索：" >
            <el-input  class="input-width" placeholder="企业名称" v-model="tempFind.enterpriseName"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
                <el-button
            type="primary"
            icon="el-icon-plus"
             style="float:right"
            @click="showCreate"  
            size="mini"
          >添加</el-button>
                   <el-button
            type="info"
           icon="el-icon-refresh"  
            @click="getList"
             style="float:right;margin-right: 15px"
      
            size="mini"
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

      <el-table-column align="center" prop="id" label="企业id" width="300"></el-table-column>
      <el-table-column align="center" prop="enterpriseName" label="企业名" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="createTime" label="创建时间" width="170"></el-table-column>
  
      <el-table-column align="center" label="操作" width="200" >
        <template slot-scope="scope">
          <el-button
            type="warning"
             icon="el-icon-edit"
            @click="showUpdate(scope.$index)"
            style="font-size: 9px;"
        
            size="mini"
          >修改</el-button>
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="isDelete(scope.$index)"
            style="font-size: 9px;"
      
           size="mini"
          >删除</el-button>
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

import { list } from "@/api/manor/sale/enterprise";

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
      tempFind:{
        enterpriseName:""
      },
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "添加",
        find:"查找"
      },
      tempEnterprise:{
        id:"",
        enterpriseName:"",
        taxBunber:"",
        address:"",
        phoneNumber:"",
        bank:"",
        bankNumber:"",
        createTime:""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {



     getList() {
      this.listLoading = true;

      list(this.listQuery).then(response => {
        
        const data = response.data
        this.listLoading = false;
        this.list = data.content;
        this.total = data.totalElements;
        this.pageNum = data.number+1;
        this.pageRow = data.size;
        }
      );
    },


     handleResetSearch(){
       this.tempFind.enterpriseName=""
    },


    // find(){
    //   this.listLoading = true;
    //   this.api({
    //     url: "enterprise/findByMany",
    //     method: "post",
    //     data:{
    //       "pageNum":this.listQuery.pageNum,
    //       "pageRow":this.listQuery.pageRow,
    //       "enterpriseName":this.tempFind.enterpriseName
    //     }
    //   }).then(data => {
    //     this.list = data.content;
    //     this.total = data.totalPages;
    //     this.pageNum = data.number;
    //     this.pageRow = data.size;
    //     this.listLoading = false;
    //     this.dialogFormVisible = false;
    //   });
    // },

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

    // showCreate() {
    //   //显示新增对话框
    //   this.tempEnterprise.id = "";
    //   this.tempEnterprise.enterpriseName = "";
    //   this.tempEnterprise.taxBunber = "";
    //   this.tempEnterprise.address = "";
    //   this.tempEnterprise.phoneNumber = "";
    //   this.tempEnterprise.bank = "";
    //   this.tempEnterprise.bankNumber = "";
    //   this.tempEnterprise.createTime = "";

    //   this.dialogStatus = "add";
    //   this.dialogFormVisible = true;
    // },

    // showFind(){
    //   this.tempEnterprise.enterpriseName = "";

    //   this.dialogStatus = "find";
    //   this.dialogFormVisible = true;
    // },
    // showUpdate($index) {
    //   //显示修改对话框
    //   this.tempEnterprise.id = this.list[$index].id;
    //   this.tempEnterprise.enterpriseName = this.list[$index].enterpriseName;
    //   this.tempEnterprise.taxBunber = this.list[$index].taxBunber;
    //   this.tempEnterprise.address = this.list[$index].address;
    //   this.tempEnterprise.phoneNumber = this.list[$index].phoneNumber;
    //   this.tempEnterprise.bank = this.list[$index].bank;
    //   this.tempEnterprise.bankNumber = this.list[$index].bankNumber;
    //   this.tempEnterprise.createTime = this.list[$index].createTime;

    //   this.dialogStatus = "update";
    //   this.dialogFormVisible = true;
    // },

    // create() {
    //   //保存经销商信息
    //   this.api({
    //     url: "enterprise/add",
    //     method: "post",
    //     data: this.tempEnterprise
    //   }).then(() => {
    //     this.getList();
    //     this.dialogFormVisible = false;
    //   });
    // },


    // update(){
    //   this.api({
    //     url: "enterprise/update",
    //     method: "post",
    //     data: this.tempEnterprise
    //   }).then(() => {
    //     this.getList();
    //     this.dialogFormVisible = false;
    //   });
    // },


   



    // isDelete($index) {
    //   this.tempEnterprise.id = this.list[$index].id;
    //   //修改商品信息
    //   this.api({
    //     url: "enterprise/delete",
    //     method: "post",
    //     data: this.tempEnterprise
    //   }).then(() => {
    //     this.getList();
    //     this.dialogFormVisible = false;
    //   });
    // }
  }
};
</script>
