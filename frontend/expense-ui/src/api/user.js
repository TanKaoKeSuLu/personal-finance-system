import request from '@/utils/request'

// 登录
export function login(data) {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

// 注册
export function register(data) {
  return request({
    url: '/api/user/register',
    method: 'post',
    data
  })
}

export const getUserInfo = (userId) => {
  return request({
    url: '/api/user/info',
    method: 'get',
    params: {
      userId
    }
  })
}

export const changePassword = (data) => {
  return request({
    url: '/api/user/changePassword',
    method: 'post',
    data
  })
}