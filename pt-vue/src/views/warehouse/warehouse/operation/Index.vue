<template>
  <div>
    <!-- 根据月份统计仓库出入库单的申请数量 -->
    <el-row>
      <el-col :span="24">
        <div>
          <el-row>
            <el-col :span="24">
              <el-divider style="size: 2px"></el-divider>
              <h4 style="padding: 5px; padding-left: 2%">根据月份统计仓库出入库单的申请数量</h4>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <div id="outAndInApplyNumByMonth" :style="{width: '100%', height: '300px'}"></div>
    <!-- 根据月份统计仓库出入库单的审批数量 -->
    <el-row>
      <el-col :span="24">
        <div>
          <el-row>
            <el-col :span="24">
              <el-divider style="size: 2px"></el-divider>
              <h4 style="padding: 5px; padding-left: 2%">根据月份统计仓库出入库单的审批数量</h4>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <div id="outAndInApprovedNumByMonth" :style="{width: '100%', height: '300px'}"></div>
    <!-- 根据月份统计物品种类数量 -->
    <el-row>
      <el-col :span="24">
        <div>
          <el-row>
            <el-col :span="24">
              <el-divider style="size: 2px"></el-divider>
              <h4 style="padding: 5px; padding-left: 2%">根据月份统计物品种类数量</h4>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <div id="goodsKindNumByMonth" :style="{width: '100%', height: '300px'}"></div>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data () {
    return {
    }
  },
  methods: {
    outAndInApplyNumByMonth: function () {
      // 准备div
      let myEcharts = this.$echarts.init(document.getElementById('outAndInApplyNumByMonth'))

      // 模拟数据
      // 1、月份
      let mouthData = ['2019-05', '2019-06', '2019-07', '2019-08', '2019-09', '2019-10', '2019-11', '2019-12', '2020-01', '2020-02', '2020-03', '2020-04']
      // 2、出库单数量
      let outNumberData = [32, 4, 8, 21, 69, 58, 35, 21, 59, 87, 33, 79]
      // 3、入库单数量
      let inNumberData = [14, 34, 95, 41, 29, 57, 50, 10, 34, 94, 79, 97]
      // 4、总数量
      let countData = [46, 38, 103, 62, 98, 115, 85, 31, 93, 181, 112, 176]

      // 指定图表的配置项和数据
      let colors = ['#409EFF', '#67C23A', '#675bba']

      let option = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '10%'
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['出库单数量', '入库单数量', '总数']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: mouthData
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '数量',
            min: 0,
            axisLine: {
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value} 个'
            }
          }
        ],
        series: [
          {
            name: '出库单数量',
            type: 'bar',
            data: outNumberData
          },
          {
            name: '入库单数量',
            type: 'bar',
            data: inNumberData
          },
          {
            name: '总数',
            type: 'line',
            data: countData
          }
        ]
      }

      // 使用刚指定的配置项和数据显示图表。
      myEcharts.setOption(option)
    },
    outAndInApprovedNumByMonth: function () {
      // 准备div
      let myEcharts = this.$echarts.init(document.getElementById('outAndInApprovedNumByMonth'))

      // 模拟数据
      // 1、月份
      let mouthData = ['2019-05', '2019-06', '2019-07', '2019-08', '2019-09', '2019-10', '2019-11', '2019-12', '2020-01', '2020-02', '2020-03', '2020-04']
      // 2、出库单数量
      let outNumberData = [32, 4, 8, 21, 69, 58, 35, 21, 59, 87, 33, 79]
      // 3、入库单数量
      let inNumberData = [14, 34, 95, 41, 29, 57, 50, 10, 34, 94, 79, 97]
      // 4、总数量
      let countData = [46, 38, 103, 62, 98, 115, 85, 31, 93, 181, 112, 176]

      // 指定图表的配置项和数据
      let colors = ['#409EFF', '#67C23A', '#675bba']

      let option = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '10%'
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['出库单数量', '入库单数量', '总数']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: mouthData
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '数量',
            min: 0,
            axisLine: {
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value} 个'
            }
          }
        ],
        series: [
          {
            name: '出库单数量',
            type: 'bar',
            data: outNumberData
          },
          {
            name: '入库单数量',
            type: 'bar',
            data: inNumberData
          },
          {
            name: '总数',
            type: 'line',
            data: countData
          }
        ]
      }

      // 使用刚指定的配置项和数据显示图表。
      myEcharts.setOption(option)
    },
    goodsKindNumByMonth: function () {
      // 准备div
      let myEcharts = this.$echarts.init(document.getElementById('goodsKindNumByMonth'))
      // 模拟数据
      // 1、月份
      let mouthData = ['2019-05', '2019-06', '2019-07', '2019-08', '2019-09', '2019-10', '2019-11', '2019-12', '2020-01', '2020-02', '2020-03', '2020-04']
      // 2、种类数量
      let kindData = [32, 4, 8, 21, 69, 58, 35, 21, 59, 87, 33, 79]

      // 指定图表的配置项和数据
      let colors = ['#409EFF', '#67C23A', '#675bba']

      let option = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '10%'
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['种类数量']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: mouthData
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '数量',
            min: 0,
            axisLine: {
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value} 个'
            }
          }
        ],
        series: [
          {
            name: '种类数量',
            type: 'bar',
            data: kindData
          }
        ]
      }

      // 使用刚指定的配置项和数据显示图表。
      myEcharts.setOption(option)
    }
  },
  mounted () {
    this.outAndInApplyNumByMonth()
    this.outAndInApprovedNumByMonth()
    this.goodsKindNumByMonth()
  }
}
</script>

<style scoped>

</style>
