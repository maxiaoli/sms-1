const config = {
  keycloak: {
    authUrl: 'http://127.0.0.1:8080/auth',
    realm: 'employee',
    clientId: 'sms-manage-static'
  },
  api: {
    baseUrl: 'http://localhost:8081',
    timeout: 15000
  }
};

export default config