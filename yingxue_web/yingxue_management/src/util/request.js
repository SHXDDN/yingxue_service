import axios from 'axios'

//创建默认实例
const instance = axios.create({
  baseURL:"http://localhost:9999/yingx",
  timeout:3000,

})

//暴露实例
export default instance;
