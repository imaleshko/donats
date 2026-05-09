import { useNavigate } from "react-router";
import { useCreateFundraiser } from "@/hooks/useCreateFundraiser.ts";
import { type SubmitEvent } from "react";
import FundraiserForm from "../FundraiserForm/FundraiserForm.tsx";
import { useFundraiserFormLogic } from "../FundraiserForm/useFundraiserFormLogic.ts";

const CreateFundraiser = () => {
  const navigate = useNavigate();
  const {
    createFundraiser,
    isPending,
    error: createError,
  } = useCreateFundraiser();
  const formLogic = useFundraiserFormLogic();

  const handleSubmit = (e: SubmitEvent) => {
    e.preventDefault();
    if (!formLogic.validateForm()) return;

    createFundraiser(
      {
        title: formLogic.formData.title.trim(),
        slug: formLogic.formData.slug.trim(),
        description: formLogic.formData.description.trim(),
        goal: formLogic.formData.goal
          ? Number(formLogic.formData.goal)
          : undefined,
        endDate: formLogic.formData.endDate || undefined,
        images: formLogic.newImages.map((img) => img.file),
      },
      { onSuccess: () => navigate("/account/fundraisers") },
    );
  };

  return (
    <FundraiserForm
      title="Створення нового збору"
      submitLabel="Створити збір"
      isPending={isPending}
      serverError={createError}
      onSubmit={handleSubmit}
      {...formLogic}
    />
  );
};

export default CreateFundraiser;
