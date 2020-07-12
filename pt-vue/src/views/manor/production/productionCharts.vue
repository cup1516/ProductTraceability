<template>
<div>

  <div class="chart-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          icon="el-icon-search"
      @click="getFindList()"
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
        <el-form :inline="true" size="small" label-width="140px" >
      
         
          <el-form-item label="年份:" width="180">
           <el-date-picker
              style="width:170px"
              v-model="tempFind.year"
              align="right"
              type="year"
              placeholder="选择年份">
            </el-date-picker>
          </el-form-item>

           <el-form-item label="农作物:">
          <el-select  v-model="tempFind.crops" placeholder="请选择农作物种类"  style="width:170px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
          </el-form-item>
         
        </el-form>
      </div>
    </el-card>
<el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据图表</span>
            <el-button
            @click="getList"
            style="float:right"
              size="mini" 
           icon="el-icon-refresh"
          >刷新</el-button>
    </el-card>
    <chart height="100%" width="100%" />
  </div>
  <div :id="id" :class="className" :style="{height:height,width:width}" >

  </div>
</div>

  
</template>

<script>
import echarts from "echarts";
import resize from "@/components/Charts/mixins/resize";
import { list ,getCrops,getFindList} from "@/api/manor/production/productionCharts";



export default {
 data() {
    return {
    };
  },
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart"
    },
    id: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "1300px"
    },
    height: {
      type: String,
      default: "800px"
    }
  },
  data() {
    return {
      chart: null,
      tempFind:{
        crops:"",
        year:""
      },
      enterpriseNames:[],
      crops:[]
    };
  },
  mounted() {
    this.initChart();
    this.getList();
    this.getCrops();
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },

 
  methods: {

    handleResetSearch(){
      this.tempFind.year="",
      this.tempFind.crops=""
    },


   getCrops() {
      getCrops().then(response => {
        const data = response.data
        this.crops = data;
        }
      );
    },


  getList() {
      this.listLoading = true;

      list(this.listQuery).then(response => {
        
        const data = response.data
        this.listLoading = false;
       this.chart.hideLoading();
        this.chart.setOption({
          series: [
            {
              name: "柱状图",
              data: data.list,
              color: ["#FF3333"]
            },
            {
              name: "折线图",
              data: data.list,
              color: ["#43cd81"]
            }
          ]
        });
        }
      );
    },

    getFindList() {
      this.listLoading = true;

      getFindList(
        {
          "year":this.tempFind.year,
          "crops":this.tempFind.crops
        }
      ).then(response => {
        
        const data = response.data
        this.listLoading = false;
       this.chart.hideLoading();
        this.chart.setOption({
          series: [
            {
              name: "柱状图",
              data: data.list,
              color: ["#FF3333"]
            },
            {
              name: "折线图",
              data: data.list,
              color: ["#43cd81"]
            }
          ]
        });
        }
      );
    },

  


    initChart() {
      this.chart = echarts.init(document.getElementById(this.id));
      const xData = (function() {
        const data = [];
        for (let i = 1; i < 13; i++) {
          data.push(i + "month");
        }
        return data;
      })();
      this.chart.setOption({
        backgroundColor: "#344b58",
        title: {
          text: "产量统计",
          x: "20",
          top: "20",
          textStyle: {
            color: "#fff",
            fontSize: "22"
          },
          subtextStyle: {
            color: "#90979c",
            fontSize: "16"
          }
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            textStyle: {
              color: "#fff"
            }
          }
        },
        grid: {
          left: "5%",
          right: "5%",
          borderWidth: 0,
          top: 150,
          bottom: 95,
          textStyle: {
            color: "#fff"
          }
        },
        legend: {
          x: "5%",
          top: "10%",
          textStyle: {
            color: "#90979c"
          },
          // data: ["柱状图", "苹果", "折线图"]
          data: ["柱状图",  "折线图"]

        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#90979c"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0
            },
            data: xData
          }
        ],
        yAxis: [
          {
            type: "value",
            splitLine: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: "#90979c"
              }
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              interval: 0
            },
            splitArea: {
              show: false
            }
          }
        ],
        dataZoom: [
          {
            show: true,
            height: 30,
            xAxisIndex: [0],
            bottom: 30,
            start: 10,
            end: 80,
            handleIcon:
              "path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z",
            handleSize: "110%",
            handleStyle: {
              color: "#d3dee5"
            },
            textStyle: {
              color: "#fff"
            },
            borderColor: "#90979c"
          },
          {
            type: "inside",
            show: true,
            height: 15,
            start: 1,
            end: 35
          }
        ],
        series: [
          {
            name: "女性",
            type: "bar",
            stack: "total",
            barMaxWidth: 35,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "rgba(255,144,128,1)",
                label: {
                  show: true,
                  textStyle: {
                    color: "#fff"
                  },
                  position: "insideTop",
                  formatter(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.wListForCount
          },

          // {
          //   name: "男性",
          //   type: "bar",
          //   stack: "total",
          //   itemStyle: {
          //     normal: {
          //       color: "rgba(0,191,183,1)",
          //       barBorderRadius: 0,
          //       label: {
          //         show: true,
          //         position: "top",
          //         formatter(p) {
          //           return p.value > 0 ? p.value : "";
          //         }
          //       }
          //     }
          //   },

          //   data: [this.mListForCount]
          // },
          {
            name: "总数",
            type: "line",
            stack: "total",
            symbolSize: 10,
            symbol: "circle",
            itemStyle: {
              normal: {
                color: "rgba(252,230,48,1)",
                barBorderRadius: 0,
                label: {
                  show: true,
                  position: "top",
                  formatter(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: [this.sumList]
          }
        ]
      });
    }
  }
};
</script>
 