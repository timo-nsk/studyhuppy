const DOMAIN : string = "49.12.242.124"

export const environment = {
  production: true,
  authServiceUrl: `http://${DOMAIN}:9084/api/v1`,
  modulServiceUrl: `http://${DOMAIN}:9080/api/v1`,
  karteiServiceUrl: `http://${DOMAIN}:9081/api/v1`,
  actuatorServiceUrl: `http://${DOMAIN}:9085/api/v1`
};
