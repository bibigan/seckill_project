<template>
  <v-col
    cols="12"
    :md="size === 2 ? 6 : size === 3 ? 3 : undefined"
  >
    <base-card
      id="basedCard"
      :height="value.prominent ? 100 : 100"
    >
<!--      <h3 class="title font-weight-bold mb-2">-->
<!--        {{ value.title }}-->
<!--      </h3>-->
      <v-data-table
        :headers="headers"
        :items="dessert"
        hide-default-footer
        class="elevation-1"
      >
        <template
          v-slot:item.img="{ item }"
        >
          <v-img
            :src="require(`@/assets/img/${item.img}`)"
            height="80%"
            width="50%"
          />
        </template>
        <template v-slot:item.total="{ item }">
          <v-chip
            :color="getColor(item.total)"
            dark
          >
            {{ item.total }}
          </v-chip>
        </template>
      </v-data-table>
    </base-card>
  </v-col>
</template>

<script>

  export default {
    name: 'OrderFeedCard',
    props: {
      size: {
        type: Number,
        required: true,
      },
      value: {
        type: Object,
        default: () => ({}),
      },
      stat: {
        type: Boolean,
      },
    },
    data () {
      return {
        headers: [
          {
            text: '商品',
            align: 'start',
            value: 'img',
          },
          { text: '标题', value: 'title' },
          { text: '单价(元)', value: 'price' },
          { text: '数量', value: 'number' },
          { text: '总价(元)', value: 'total' },
          { text: '订单编号', value: 'ocode' },
          { text: '创建时间', value: 'createTime' },
        ],
        dessert: [
          {
            title: '越野',
            createTime: '2020-12-22 00:55:55',
            img: 'umbrella.jpg',
            ocode: '20201222005555',
            price: 435.00,
            number: 2,
            total: 0.00,
          },
        ],
      }
    },
    mounted () {
      this.getValue()
    },
    methods: {
      getStatus (data) {
        this.status = data
      },
      getColor (total) {
        if (total > 400) return 'red'
        else if (total > 200) return 'orange'
        else return 'green'
      },
      getValue () {
        this.dessert[0].title = this.value.title
        this.dessert[0].createTime = this.value.createTime
        this.dessert[0].img = this.value.img
        this.dessert[0].ocode = this.value.ocode
        this.dessert[0].price = this.value.price
        this.dessert[0].number = this.value.number
        this.dessert[0].total = this.value.price * this.value.number
      },
    },
  }
</script>

<style>
  .v-image__image {
    transition: .3s linear;
  }
</style>
