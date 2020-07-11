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
            size="mini"
            @click="find"
           icon="el-icon-search"
          >搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          size="mini"
        icon="el-icon-refresh"
         @click="handleResetSearch()"
         >重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true"  size="small" :model="tempFind" label-width="140px">
          <el-form-item label="区域名称：">
            <el-input  class="input-width" v-model="tempFind.farmlandLocationName" placeholder="区域名称："></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
     </el-card>
        <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>
        <!-- @click="handleAddProduct"  -->
      <span>数据列表</span>
          <el-button
            type="primary"
            icon="el-icon-plus"
             style="float:right"
               size="mini"
   

            @click="add"
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

      <!-- <el-table-column align="center" prop="farmlandLocationId" label="id" width="270"></el-table-column> -->
      <el-table-column align="center" prop="farmlandLocationName" label="区域名" width="255"></el-table-column>
      <el-table-column align="center" prop="farmlandPrincipal" label="负责人" width="170" ></el-table-column>
      <el-table-column align="center" prop="farmlandArea" label="地区" width="170"></el-table-column>
      <el-table-column align="center" prop="precipitation" label="年降水量" width="170"></el-table-column>
      <el-table-column align="center" prop="climate" label="气候" width="170"></el-table-column>
      <!-- <el-table-column align="center" prop="farmlandExplain" label="备注" width="400"></el-table-column> -->

      <el-table-column align="center" label="操作" width="200" >
        <template slot-scope="scope">
          <el-button
            type="warning"
            icon="el-icon-edit"
            @click="update(scope.$index)"
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
import { list,isDelete } from "@/api/manor/planting/farmlandLocation";

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
      tempFarmlandLocation:{
        precipitation:"",
        farmlandLocationId:"",
        farmlandLocationName:"",
        farmlandPrincipal:"",
        farmlandArea:"",
        farmlandExplain:"",
        climate:""
      },
      tempFind:{
        farmlandLocationName:""
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
      this.tempFind.farmlandLocationName=""
    },

    add(){
     this.$router.push({path:'/detail/addFarmlandLocation'});
    },


     update($index){
          this.$router.push({path:'/detail/updateFarmlandLocation',
          query:{id:this.list[$index].farmlandLocationId}
        });
      },

    find(){
      this.listLoading = true;
      this.api({
        url: "painting/farmlandLocation/findByMany",
        method: "post",
        data:{
          "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "farmlandLocationName": this.tempFind.farmlandLocationName,
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


    
    isDelete($index) {
      this.tempFarmlandLocation.farmlandLocationId = this.list[$index].farmlandLocationId;
      //修改商品信息
      isDelete(
        this.tempFarmlandLocation
      ).then(response =>{

        this.getList();
        this.dialogFormVisible = false;
      })
     
    }
  }
};
</script>
