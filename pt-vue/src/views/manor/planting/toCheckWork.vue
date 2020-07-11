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
            size="mini"
          >搜索
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
        <el-form :inline="true" :model="tempFind" size="small" label-width="140px">
                    <el-form-item label="作业编号:">
            <el-input  class="input-width" v-model="tempFind.id" placeholder="作业编号"></el-input>
          </el-form-item>
          <el-form-item label="地块编号:">
             <el-select  v-model="tempFind.field" placeholder="请选择地块"  style="width:170px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
          </el-form-item>
          <el-form-item label="作业类型:">
            <el-select style="width:170px" v-model="tempFind.work" placeholder="请选择作业类型" >
                <el-option  v-for="item in workTypes" :key="item.name" :label="item.name" :value="item.name"></el-option>
              </el-select>
           </el-form-item>
            <el-form-item label="员工姓名:">
            <el-input class="input-width"  v-model="tempFind.staff"  placeholder="请输入员工姓名"></el-input>
          </el-form-item>
          <el-form-item label="开始时间:">
              <el-date-picker
               v-model="tempFind.stime"
                type="date"
                    style="width:170px"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
                   <el-form-item label="截止时间:" label-width="140px">
              <el-date-picker
                 v-model="tempFind.etime"
                type="date"
                 style="width:170px"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    </el-card>
        <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>

      <span>数据列表</span>
          <el-button
          type="primary"
           icon="el-icon-plus"
            style="float:right"
            size="mini"
            @click="addWork"

          >添加</el-button>
              <el-button
            icon="el-icon-refresh"
            @click="getList"
              size="mini"
             style="float:right;margin-right: 15px"

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

      <el-table-column align="center" prop="id" label="作业编号" width="270"></el-table-column>
      <el-table-column align="center" prop="staff" label="责任人" width="100"></el-table-column>
      <el-table-column align="center" prop="date" label="创建时间" width="150"></el-table-column>
    
      <el-table-column align="center" prop="checkUser" label="审批人" width="150"></el-table-column>
        <el-table-column align="center"  label="状态" width="100">
        <template slot-scope="scope">

          <div v-if="scope.row.checkNumber == 0" style="color:#800000" > 待送审</div>
          <div v-if="scope.row.checkNumber == 1" style="color:#0000FF"> 待审核</div>
          <div v-if="scope.row.checkNumber == 2" style="color:#008800"> 审核通过</div>
          <div v-if="scope.row.checkNumber == 3" style="color:#FF0000"> 审核未通过</div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300" >
        <template slot-scope="scope">
          <el-button
            type="success"
              size="mini"
            style="font-size: 9px;"
    
            @click="pass(scope.$index)"
             icon="el-icon-check"
          >通过</el-button>
          <el-button
            type="info"
              size="mini"
           @click="get(scope.$index, scope.row)"
            style="font-size: 9px;"
         
             icon="el-icon-tickets"
          >查看</el-button>

          <el-button
            type="danger"
          icon="el-icon-d-arrow-right"
            style="font-size: 9px;"
              size="mini"
          @click="checkBack(scope.$index)"
          >打回</el-button>
          
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
  import ImgUpload from "./imgUpload";
  import { list,find,pass,checkBack } from "@/api/manor/planting/toCheckWork";
import {getWorkTypes,getFarmlandRegionId} from "@/api/manor/planting/work/add";



export default {
  components: {ImgUpload},
  data() {
    return {
      tempFind:{},
      formLabelWidth: '120px',
      fileList: [],
      url: '',
      total: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: ""
      },
      tempFind:{
        stime:"",
        etime:"",
        id:"",
        crops:"",
        work:"",
        staff:"",
        field:""
      },
      batch:[],
      tempWork:{
        id:"",
        work:"",
        crops:"",
        picture:"",
        video:"",
        remark:"",
        staff:"",
        amount:"",
        date:"",
        findFarmlandRegionId:"",
        stime:"",
        etime:"",
        batch:"",
        materialName:"",
        materialInformation:"",
        materialCompany:"",
        check:'',
        checkUser:''
      },
      crops:[],
      workTypes:[],
      farmlandRegion:[]
    };
  },

  created() {
    this.getList();
    this.getFarmlandRegionId();
    // this.getBatch();
    // this.getCrops();
    this.getWorkTypes();
  },

 methods:{

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

   pass($index){
     this.tempWork.id = this.list[$index].id;
     pass(
        this.tempWork
     ).then(response =>{
        this.getList();
       })
   },

    checkBack($index){
         this.tempWork.id = this.list[$index].id;
      checkBack(
        this.tempWork
      ).then(response =>{
        this.getList();
      })
   },
   

   get($index){
      this.$router.push({path:'/detail/toCheckWorkDetail',
       query:{id:this.list[$index].id}
     });
   },

  
    addWork() {
        this.$router.push({path:'/planting/work/addWork'});
      },


    getWorkTypes() {
      getWorkTypes().then(response => {
        const data = response.data
        this.workTypes = data;
        }
      );
    },

    getFarmlandRegionId() {
      getFarmlandRegionId().then(response => {
        const data = response.data
      this.farmlandRegion = data;
        }
      );
    },

   handleResetSearch(){
     this.tempFind.stime="",
     this.tempFind.etime="",
     this.tempFind.id = "",
     this.tempFind.work = "",
     this.tempFind.field = "",
     this.tempFind.staff ="",
     this.tempFind.crops=""
   },

      getUserRealName(){
      this.api({
        url:"yun/login/getInfo",
        method:"get",
      }).then(()=>{
        var a = data.realName

      })
    },
   
    uploadImg () {
        this.tempWork.picture = this.$refs.imgUpload.url
        console.log(this.tempWork.picture+"aaaaaa")
      },
    
    find(){
        this.listLoading = true;
      find({
           "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "stime":this.tempFind.stime,
          "etime":this.tempFind.etime,
          "id":this.tempFind.id,
          "crops":this.tempFind.crops,
          "work":this.tempFind.work,
          "staff":this.tempFind.staff,
          "findFarmlandRegionId":this.tempFind.field
      }).then(response =>{
        const data = response.data
        this.list = data.content;
        this.total = data.totalPages;
        this.pageNum = data.number;
        this.pageRow = data.size;
        this.listLoading = false;
      })
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
      this.tempWork.id='';
      this.tempWork.work='';
      this.tempWork.crops='';
      this.tempWork.picture='';
      this.tempWork.video='';
      this.tempWork.remark='';
      this.tempWork.staff='';
      this.tempWork.amount='';
      this.tempWork.date='';
      this.tempWork.findFarmlandRegionId='';
      this.tempWork.batch='';
      this.tempWork.materialName='';
      this.tempWork.materialInformation='';
      this.tempWork.materialCompany='';
 
      //显示新增对话框
    this.api({
        url:"yun/login/getInfo",
        method:"get",
      }).then(data => {
        this.tempWork.staff= data.realName
      })
      this.dialogStatus = "add";
      this.dialogFormVisible = true;
    },


    showFind(){
      this.dialogStatus = "find";
      this.dialogFormVisible = true;
    },


    showUpdate($index) {
      this.tempWork.findFarmlandRegionId = this.list[$index].findFarmlandRegionId;
      this.tempWork.batch = this.list[$index].batch;
      this.tempWork.id = this.list[$index].id;
      this.tempWork.work=this.list[$index].work,
      this.tempWork.crops=this.list[$index].crops,
      this.tempWork.picture=this.list[$index].picture,
      this.tempWork.video=this.list[$index].video,
      this.tempWork.remark=this.list[$index].remark;
      this.tempWork.staff=this.list[$index].staff;
      this.tempWork.amount = this.list[$index].amount;
      this.tempWork.materialCompany = this.list[$index].materialCompany;
      this.tempWork.materialName = this.list[$index].materialName;
      this.tempWork.materialInformation = this.list[$index].materialInformation;
      this.tempWork.date = this.list[$index].date;

      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },

    create() {
      //保存
      this.api({
        url: "/painting/work/add",
        method: "post",
        data: this.tempWork
      }).then(() => {
        this.getList();
        this.tempWork.materialName = "",
        this.tempWork.materialInformation = "",
        this.tempWork.amount = "",
        this.tempWork.materialCompany = ""
        this.dialogFormVisible = false;
 
      });
    },

    updateFruitEnterprises() {
      //修改经销商信息
      this.api({
        url: "/painting/work/update",
        method: "post",
        data: this.tempWork
      }).then(() => {
        alert("修改成功");
        this.getList();
        this.dialogFormVisible = false;
      });
    },


    

    isDelete($index) {
      this.tempWork.id = this.list[$index].id; 
      //修改商品信息
      this.api({
        url: "painting/work/delete",
        method: "post",
        data: this.tempWork
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    }
  }
};

</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>


