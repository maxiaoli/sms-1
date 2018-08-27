import './keycloak'
import config from '../../config/config'

let keycloak = Keycloak({
  url: config.keycloak.url,
  realm: config.keycloak.realm,
  clientId: config.keycloak.clientId
});

keycloak.init({
  onLoad: 'login-required',
  refreshToken: true
}).then(function(authenticated) {
  console.log(authenticated ? 'authenticated' : 'not authenticated');
}).catch(function() {
  console.log('failed to initialize');
});

//定时刷新token
// token 过期时间为300s 默认
// 设定没过290s请求判断刷新，距过期时间30以内就请求刷新
setInterval(() => {
  //Token 过期，需要刷新Token
  keycloak.updateToken(30).then(function(refreshed) {
    if (refreshed) {
      console.log('Token was successfully refreshed');
    } else {
      console.log('Token is still valid');
    }
  }).catch(function() {
    console.log('Failed to refresh the token, or the session has expired');
  });

}, 290000);

export default keycloak
