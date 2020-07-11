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
          @click="find()"
          size="mini">搜索</el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="mini"
          icon="el-icon-refresh"
         >重置</el-button>
      </div>

      <div style="margin-top: 15px">
        <el-form :inline="true"  size="small" label-width="140px" :model="tempFind">
          <el-form-item label="输入搜索：" >
            <el-input  class="input-width" placeholder="物料名称" v-model="tempFind.materialName"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
        <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
          <el-button
            type="primary"
             style="float:right"
            @click="showCreate" 
            icon="el-icon-plus"
             size="mini"
          >添加</el-button>
              <el-button
            @click="getList"
             style="float:right;margin-right: 15px"
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

      <el-table-column align="center" prop="materialName" label="物料名称" width="200"></el-table-column>
      <el-table-column align="center" prop="materialCompany" label="物料公司" width="200" ></el-table-column>
      <el-table-column align="center" prop="materialInformation" label="详细信息" width="550" ></el-table-column>
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
            @click="isDelete(scope.$index)"
            style="font-size: 9px;"
            size="mini"
          icon="el-icon-delete"
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="small-space"
        :model="tempMaterial"
        label-position="left"
        label-width="90px"
        style="width: 500px; margin-left:50px;"
      >

        <el-form-item label="物料名称:">
          <el-input type="text" v-model="tempMaterial.materialName"></el-input>
        </el-form-item>

        <el-form-item label="物料公司:">
          <el-input type="text" v-model="tempMaterial.materialCompany"></el-input>
        </el-form-item>

        <el-form-item label="详细信息:">
          <el-input type="textarea" v-model="tempMaterial.materialInformation"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='add'"  type="success" @click="create">创 建</el-button>
        <el-button v-if="dialogStatus=='find'"  type="primary" @click="find">查 找</el-button>
        <el-button v-if="dialogStatus=='update'"  type="warning" @click="update">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { list } from "@/api/manor/planting/material";

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
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "添加",
        find:"查找"
      },
      tempCrops:{
        id:"",
        name:'',
        remark:'',
      },
      tempMaterial:{
        id:'',
        materialName:'',
        materialInformation:'',
        materialCompany:"",
        
      },
      tempFind:{
        materialName:''
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
      this.tempFind.materialName=''
    },


    find(){
      this.listLoading = true;
      this.api({
        url: "painting/material/find",
        method: "post",
        data:{
          "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "materialName": this.tempFind.materialName,
        }
      }).then(data => {
        this.list = data.content;
        this.total = data.totalElements;
        this.pageNum = data.number+1;
        this.pageRow = data.size;
        this.listLoading = false;
        this.dialogFormVisible = false;
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


    showCreate() {
      //显示新增对话框
      this.tempMaterial.id = "";
      this.tempMaterial.materialName = "";
      this.tempMaterial.materialInformation = "";
      this.tempMaterial.materialCompany = "";
      this.dialogStatus = "add";
      this.dialogFormVisible = true;
    },


    showUpdate($index) {
      //显示修改对话框
      this.tempMaterial.id =this.list[$index].id;
      this.tempMaterial.materialName =this.list[$index].materialName;
      this.tempMaterial.materialInformation =this.list[$index].materialInformation;
      this.tempMaterial.materialCompany =this.list[$index].materialCompany;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },

    create() {
      
      //保存
      this.api({
        url: "/painting/material/add",
        method: "post",
        data: this.tempMaterial
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },

    update() {
      this.api({
        url: "/painting/material/update",
        method: "post",
        data: this.tempMaterial
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },


  


    isDelete($index) {
      this.tempMaterial.id = this.list[$index].id;
      //修改商品信息
      this.api({
        url: "/painting/material/delete",
        method: "post",
        data: this.tempMaterial
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    }
  }
};
</script>
