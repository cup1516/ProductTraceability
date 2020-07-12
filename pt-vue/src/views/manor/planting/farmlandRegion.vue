<template>
  <div class="app-container">

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
        <el-form :inline="true"  :model="tempFind" size="small" label-width="140px">

          <el-form-item label="地块编号：">
            <el-input  class="input-width" v-model="tempFind.farmlandRegionId" placeholder="请输入地块编号"></el-input>
          </el-form-item>

         <el-form-item label="区域名称" >
          <el-select style="width:200px" v-model="tempFind.farmlandLocationName" placeholder="请选择" value-key="farmlandLocationName">
            <el-option  v-for="item in farmlandLocation" :key="item.farmlandLocationName" :label="item.farmlandLocationName" :value="item.farmlandLocationName"></el-option>
          </el-select>
        </el-form-item>

          <el-form-item label="地块类型">
         <el-select style="width:200px" v-model="tempFind.farmlandRegionType" placeholder="请选择" value-key="name">
            <el-option  v-for="item in farmlandRegionTypes" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
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
             style="float:right"
               size="mini"
            @click="add"            
            icon="el-icon-plus"

          >添加
          </el-button>
              <el-button
            icon="el-icon-refresh"  
            @click="getList"
              size="mini"
             style="float:right;margin-right: 15px"
            
          >刷新</el-button>
    </el-card>

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

      <el-table-column align="center" prop="farmlandRegionId" label="id" width="150"></el-table-column>
      <el-table-column align="center" prop="farmlandLocationName" label="隶属区域" style="width: 100px;"></el-table-column>
      <el-table-column align="center" prop="farmlandRegionPrincipal" label="负责人" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="farmlandRegionArea" label="区块面积" width="170"></el-table-column>
      <el-table-column align="center" prop="farmlandRegionType" label="田地类型" width="170"></el-table-column>
      <el-table-column align="center" prop="bulkDensity" label="容重（g/cm3）" width="170"></el-table-column>
      <el-table-column align="center" prop="porosity" label="总孔隙度" width="170"></el-table-column>
      <el-table-column align="center" prop="ph" label="PH值" width="170"></el-table-column>
      <el-table-column align="center" prop="salt" label="含盐量" width="170"></el-table-column>
      <el-table-column align="center" prop="organic" label="有机质含量（g/kg）" width="170"></el-table-column>
      <el-table-column align="center" prop="n" label="含氮量（g/kg）" width="170"></el-table-column>
      <el-table-column align="center" prop="p" label="含磷量（g/kg）" width="170"></el-table-column>
      <el-table-column align="center" prop="k" label="含钾量（g/kg）" width="170"></el-table-column>
      <el-table-column align="center" prop="soilPermeabilityCoefficient" label="土壤渗透系数（cm/s）" width="170"></el-table-column>
      
      <el-table-column align="center" label="操作" width="200" >
        <template slot-scope="scope">
          <el-button
            type="warning"
             @click="get(scope.$index, scope.row)"
            style="font-size: 9px;"

            size="mini"
            icon="el-icon-edit"
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

import { list,isDelete,getAllFarmlandLocation,getFarmlandRegionType ,find} from "@/api/manor/planting/farmlandRegion";

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
      farmlandRegionTypes:[],
      farmlandLocation:[],
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "添加",
        find:"查找"
      },
      tempFind:{
        farmlandRegionId:"",
        farmlandLocationName:"",
        farmlandRegionType:""
      },
      tempFarmlandRegion:{
        farmlandRegionId:"",
        farmlandRegionPrincipal:"",
        farmlandRegionArea:"",
        farmlandRegionExplain:"",
        farmlandRegionType:"",
        bulkDensity:"",
        porosity:"",
        ph:"",
        salt:"",
        organic:"",
        n:"",
        p:"",
        k:"",
        soilPermeabilityCoefficient:"",
        farmlandLocationName:"",
      }
    };
  },

  created() {
    this.getList();
    this.getAllFarmlandLocation();
    this.getFarmlandRegionType();
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


      get($index){
          this.$router.push({path:'/detail/updateFarmlandRegion',
          query:{id:this.list[$index].farmlandRegionId}
        });
      },

      handleResetSearch(){
        this.tempFind.farmlandRegionId="",
        this.tempFind.farmlandLocationName="",
        this.tempFind.farmlandRegionType=""
      },

    add(){
     this.$router.push({path:'/detail/addFarmlandRegion'});
    },
   
 
    getFarmlandRegionType(){
      getFarmlandRegionType().then(response =>{
        const data = response.data
        this.farmlandRegionTypes = data;
      })
    },

    getAllFarmlandLocation(){
      getAllFarmlandLocation().then(response =>{
        const data = response.data
        this.farmlandLocation = data;
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


    showUpdate($index) {
      //显示修改对话框
      this.tempFarmlandRegion.farmlandRegionId =this.list[$index].farmlandRegionId;
      this.tempFarmlandRegion.farmlandRegionPrincipal =this.list[$index].farmlandRegionPrincipal;
      this.tempFarmlandRegion.farmlandRegionArea =this.list[$index].farmlandRegionArea;
      this.tempFarmlandRegion.farmlandRegionExplain =this.list[$index].farmlandRegionExplain;
      this.tempFarmlandRegion.farmlandRegionType =this.list[$index].farmlandRegionType;
      this.tempFarmlandRegion.bulkDensity =this.list[$index].bulkDensity;
      this.tempFarmlandRegion.porosity =this.list[$index].porosity;
      this.tempFarmlandRegion.ph =this.list[$index].ph;
      this.tempFarmlandRegion.salt =this.list[$index].salt;
      this.tempFarmlandRegion.organic =this.list[$index].organic;
      this.tempFarmlandRegion.n =this.list[$index].n;
      this.tempFarmlandRegion.p =this.list[$index].p;
      this.tempFarmlandRegion.k =this.list[$index].k;
      this.tempFarmlandRegion.soilPermeabilityCoefficient =this.list[$index].soilPermeabilityCoefficient;
      this.tempFarmlandRegion.farmlandLocationName =this.list[$index].farmlandLocationName;

      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },


    find(){
      find(
        {
          "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "farmlandRegionId": this.tempFind.farmlandRegionId,
          "farmlandRegionType": this.tempFind.farmlandRegionType,
          "farmlandLocationName": this.tempFind.farmlandLocationName
        }
      ).then(response =>{
        const data = response.data
         this.list = data.content;
        this.total = data.totalElements;
        this.pageNum = data.number+1;
        this.pageRow = data.size;
        this.listLoading = false;
        this.dialogFormVisible = false;
      })
    },

    
    isDelete($index) {
      this.tempFarmlandRegion.farmlandRegionId = this.list[$index].farmlandRegionId;
      isDelete(
        this.tempFarmlandRegion
      ).then(response =>{
        this.getList();
        this.dialogFormVisible = false;
      })
    }

  }
};
</script>
