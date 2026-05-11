import axios from "axios";
const baseURL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

export const api = axios.create({
  baseURL,
  withCredentials: true,
});

let accessToken: string | null = null;

export const setAccessToken = (token: string | null) => {
  accessToken = token;
};

api.interceptors.request.use((config) => {
  if (accessToken) {
    config.headers.set("Authorization", `Bearer ${accessToken}`);
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    const status = error.response?.status;

    if (
      (status === 401 || status === 403) &&
      !originalRequest._retry &&
      !originalRequest.url?.includes("/auth/refresh") &&
      !originalRequest.url?.includes("/auth/login") &&
      !originalRequest.url?.includes("/auth/register")
    ) {
      originalRequest._retry = true;

      try {
        const response = await axios.post(
          `${baseURL}/auth/refresh`,
          {},
          { withCredentials: true },
        );

        const newAccessToken = response.data.accessToken;
        setAccessToken(newAccessToken);

        originalRequest.headers.set(
          "Authorization",
          `Bearer ${newAccessToken}`,
        );
        return api(originalRequest);
      } catch {
        setAccessToken(null);
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  },
);
