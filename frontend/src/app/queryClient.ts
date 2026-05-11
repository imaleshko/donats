import { QueryClient } from "@tanstack/react-query";
import { isAxiosError } from "axios";

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: (failureCount, error) => {
        if (isAxiosError(error)) {
          const status = error.response?.status;
          if (status === 401 || status === 403 || status === 404) {
            return false;
          }
        }
        return failureCount < 3;
      },
    },
  },
});
