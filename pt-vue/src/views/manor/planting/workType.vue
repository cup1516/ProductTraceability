<template>
  <div class="app-container">
    <div class="filter-container">

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
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span>{{scope.$index+1 }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column align="center" prop="id" label="id" width="270"></el-table-column> -->
      <el-table-column align="center" prop="name" label="作业类型" width="990"></el-table-column>
  
      <el-table-column align="center" label="操作" width="200" >
        <template slot-scope="scope">
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="small-space"
        :model="tempWorkType"
        label-position="left"
        label-width="150px"
        style="width: 500px; margin-left:50px;"
      >
        <el-form-item label="id" v-if="dialogStatus=='update'">
          <el-input type="text" v-model="tempWorkType.id" :disabled="true">
          </el-input>
        </el-form-item>

        <el-form-item label="作业名称：">
          <el-input type="text" v-model="tempWorkType.name"></el-input>
        </el-form-item>

        <el-form-item label="是否需要物料：">
          <!-- <el-input type="text" v-model="tempWorkType.name"></el-input> -->
          <el-switch
            v-model="tempWorkType.needMaterial"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
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
import { list,create,isDelete } from "@/api/manor/planting/workType";

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
      tempWorkType:{
        name:'',
        id:'',
        needMaterial:''
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
      this.tempFind.name=''
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
      this.tempWorkType.id = "";
      this.tempWorkType.name = "";
      this.dialogStatus = "add";
      this.dialogFormVisible = true;
    },


    showUpdate($index) {
      //显示修改对话框
      this.tempCrops.id =this.list[$index].id;
      this.tempCrops.name =this.list[$index].name;
      this.tempCrops.remark =this.list[$index].remark;

      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },


    create() {
      create(
      this.tempWorkType
      ).then(response =>{
        this.getList();
        this.dialogFormVisible = false;
      })

    },



    isDelete($index) {
      this.tempWorkType.id = this.list[$index].id;
      isDelete(
        this.tempWorkType
      ).then(response =>{
        this.getList();
        this.dialogFormVisible = false;
      })
     
    }


  }
};
</script>
