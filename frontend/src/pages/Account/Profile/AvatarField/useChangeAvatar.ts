import { useMutation, useQueryClient } from "@tanstack/react-query";
import { imageApi } from "@/pages/Account/imageApi.ts";
import { accountApi, type User } from "@/pages/Account/accountApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

const useChangeAvatar = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: async (file: File) => {
      const urls = await imageApi.uploadImages([file]);
      if (!urls || urls.length === 0) {
        throw new Error("Не вдалося отримати URL зображення");
      }

      return accountApi.changeAvatar({ avatarUrl: urls[0] });
    },

    onSuccess: (updatedUser: User) => {
      queryClient.setQueryData(["user"], updatedUser);
    },
  });

  return {
    changeAvatar: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка оновлення аватарки"),
  };
};

export default useChangeAvatar;
