import { useMutation } from "@tanstack/react-query";
import { donationApi, type DonationInitRequest } from "./donationApi.ts";
import { isAxiosError } from "axios";

interface LiqPayChain {
  on(
    event: "liqpay.callback",
    cb: (data: { status: string }) => void,
  ): LiqPayChain;
  on(event: "liqpay.ready" | "liqpay.close", cb: () => void): LiqPayChain;
}

interface LiqPayCheckoutInstance {
  init(options: {
    data: string;
    signature: string;
    embedTo: string;
    language: string;
    mode: "popup";
  }): LiqPayChain;
}

declare global {
  interface Window {
    LiqPayCheckout: LiqPayCheckoutInstance;
  }
}

interface UseInitDonationProps {
  onSuccessPayment?: () => void;
  onFailedPayment?: () => void;
}

export const useInitDonation = ({
  onSuccessPayment,
  onFailedPayment,
}: UseInitDonationProps) => {
  const mutation = useMutation({
    mutationFn: (data: DonationInitRequest) => donationApi.initDonation(data),
    onSuccess: (response) => {
      if (window.LiqPayCheckout) {
        window.LiqPayCheckout.init({
          data: response.data,
          signature: response.signature,
          embedTo: "#liqpay_checkout",
          language: "uk",
          mode: "popup",
        }).on("liqpay.callback", (data) => {
          if (data.status === "success") {
            if (onSuccessPayment) onSuccessPayment();
          } else if (
            data.status === "failure" ||
            data.status === "error" ||
            data.status === "reversed"
          ) {
            onFailedPayment?.();
          }
        });
      } else {
        throw new Error("Не вдалося розпочати донат");
      }
    },
  });

  const getErrorMessage = (): string | null => {
    if (!mutation.error) return null;
    if (isAxiosError(mutation.error)) {
      return (
        mutation.error.response?.data?.detail || "Помилка ініціалізації донату"
      );
    }
    return mutation.error.message || "Невідома помилка";
  };

  return {
    initDonation: mutation.mutate,
    isPending: mutation.isPending,
    error: getErrorMessage(),
  };
};
