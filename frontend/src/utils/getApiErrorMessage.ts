import { isAxiosError } from "axios";

export const getApiErrorMessage = (
  error: unknown,
  fallback: string,
): string | null => {
  if (!error) return null;
  if (isAxiosError(error)) {
    return error.response?.data?.detail || fallback;
  }
  return error instanceof Error ? error.message : "Невідома помилка";
};
