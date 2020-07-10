<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="单位编码" prop="unitCode">
        <el-input
          v-model="queryParams.unitCode"
          placeholder="请输入单位编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="单位状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dealer:unit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dealer:unit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dealer:unit:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="unitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单位编号" width="280" align="center" prop="unitId" />
      <el-table-column label="单位编码" align="center" prop="unitCode" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dealer:unit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dealer:unit:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.current"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <!-- 添加或修改单位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="单位编码" prop="unitCode">
          <el-input v-model="form.unitCode" placeholder="请输入编码名称" />
        </el-form-item>
        <el-form-item label="单位状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUnit ,getUnit ,addUnit ,updateUnit,delUnit} from "@/api/dealer/unit";

export default {
  name: "unit",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 单位表格数据
      unitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        unitCode: undefined,
        unitName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitName: [
          { required: true, message: "单位名称不能为空", trigger: "blur" }
        ],
        unitCode: [
          { required: true, message: "单位编码不能为空", trigger: "blur" }
        ],
        unitSort: [
          { required: true, message: "单位顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询单位列表 */
    getList() {
      this.loading = true;
      listUnit(this.queryParams).then(response => {
        this.unitList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 单位状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        unitId: undefined,
        unitCode: undefined,
        unitName: undefined,
        unitSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.unitId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加单位";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const unitId = row.unitId || this.ids
      getUnit(unitId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单位";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.unitId != undefined) {
            updateUnit(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response.msg);
            });
          } else {
            addUnit(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response.msg);
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const unitIds = row.unitId || this.ids;
      this.$confirm('是否确认删除单位编号为"' + unitIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          unit: "warning"
        }).then(function() {
          return delUnit(unitIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
  }
};
</script>