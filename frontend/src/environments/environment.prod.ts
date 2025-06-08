const DOMAIN : string = "49.12.242.124"

export const environment = {
  production: true,
  authServiceUrl: `https://${DOMAIN}:9084/api/v1`,
  modulServiceUrl: `http://${DOMAIN}:9080/api/v1`,
  karteiServiceUrl: `http://${DOMAIN}:9081/api/v1`,
  actuatorServiceUrl: `http://${DOMAIN}:9085/api/v1`,
  authApiUrl: '/api/auth/v1',
  modulApiUrl: '/api/modul/v1',
  karteiApiUrl: '/api/kartei/v1',
  mindmapApiUrl: '/api/mindmap/v1',
  mailApiUrl: '/api/mail/v1',
  actuatorApiUrl: '/api/actuator/v1',
};
