export type NewImageEntry = { file: File; previewUrl: string };

export const INITIAL_FORM = {
  title: "",
  slug: "",
  description: "",
  goal: "",
  endDate: "",
};

export const validate = (form: typeof INITIAL_FORM) => {
  const errors: Record<string, string> = {};
  if (!form.title.trim()) errors.title = "Назва обов'язкова";
  if (!form.slug.trim()) errors.slug = "Slug обов'язковий";
  else if (!/^[a-z0-9-]+$/.test(form.slug))
    errors.slug = "Тільки малі літери, цифри та дефіс";
  if (!form.description.trim()) errors.description = "Опис обов'язковий";
  if (form.goal && Number(form.goal) <= 0)
    errors.goal = "Ціль має бути більшою за 0";
  return errors;
};
