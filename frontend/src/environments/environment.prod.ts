// src/environments/environment.prod.ts
const DOMAIN : string = "49.12.242.124"

export const devEnvironment = {
  production: true,
  authServiceUrl: `http://${DOMAIN}:9084`,
  modulServiceUrl: `http://${DOMAIN}:9080/api`,
  karteiServiceUrl: `http://${DOMAIN}:9081/api`,
  actuatorServiceUrl: `http://${DOMAIN}:9085/api/v1`
};
