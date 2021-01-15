<template>
  <span>{{ seckillString }} {{ time }}</span>
</template>

<script>
  export default {
    name: 'BaseTimeCount',
    props: {
      endTime: {
        type: String,
      },
      startTime: {
        type: String,
      },
    },
    data () {
      return {
        time: '',
        flag: false,
        seckillString: '',
        status: -2,
      }
    },
    mounted () {
      const time = setInterval(() => {
        if (this.flag) {
          clearInterval(time)
        }
        this.getStatus()
        this.sendMsg()
      }, 500)
    },
    methods: {
      timeDown (time) {
        const nextTime = time
        const nowTime = new Date()
        const leftTime = parseInt((nextTime.getTime() - nowTime.getTime()) / 1000)
        // eslint-disable-next-line no-unused-vars
        const d = parseInt(leftTime / (24 * 60 * 60))
        const h = this.formate(parseInt((leftTime / (60 * 60)) % 24))
        const m = this.formate(parseInt((leftTime / 60) % 60))
        const s = this.formate(parseInt(leftTime % 60))
        if (leftTime <= 0) {
          this.flag = true
          this.$emit('time-end')
        }
        this.time = `${d}天 ${h}:${m}:${s}` // 需要修改时间样式的话 ，比如需要什么30分钟倒计时，就只保留分和秒
      },
      formate (time) {
        if (time >= 10) {
          return time
        } else {
          return `0${time}`
        }
      },
      getStatus () {
        // eslint-disable-next-line no-unused-vars
        const startTime = new Date(this.startTime)
        // eslint-disable-next-line no-unused-vars
        const endTime = new Date(this.endTime)
        // eslint-disable-next-line no-unused-vars
        const nowTime = new Date()
        if ((nowTime.getTime() - endTime.getTime()) > 0) {
          this.seckillString = '秒杀已结束'
          this.status = 1
        } else if ((nowTime.getTime() - startTime.getTime()) > 0) {
          this.seckillString = '距离秒杀结束还有'
          this.status = 0
          this.timeDown(endTime)
        } else {
          this.seckillString = '距离秒杀开始还有'
          this.status = -1
          this.timeDown(startTime)
        }
      },
      sendMsg () {
        // func: 是父组件指定的传数据绑定的函数，this.msg:子组件给父组件传递的数据
        this.$emit('sendStatus', this.status)
      },
    },
  }
</script>

<style scoped>

</style>
