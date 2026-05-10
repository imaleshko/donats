import { useNavigate } from "react-router";
import { useCreateFundraiser } from "./useCreateFundraiser.ts";
import { type SubmitEvent } from "react";
import FundraiserForm from "../Form/FundraiserForm.tsx";
import { useFundraiserFormLogic } from "../Form/useFundraiserFormLogic.ts";

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
    const data = formLogic.validateForm();
    if (!data) return;

    createFundraiser(
      {
        title: data.title,
        slug: data.slug,
        description: data.description,
        goal: data.goal ? Number(data.goal) : undefined,
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
