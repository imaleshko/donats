import { createBrowserRouter, redirect } from "react-router";
import { Layout } from "@/layout/Layout/Layout";
import { Home } from "@/pages/Home/Home";
import { queryClient } from "./queryClient.ts";
import { homeApi } from "@/pages/Home/homeApi.ts";
import Register from "@/pages/Auth/Register/Register.tsx";
import Login from "@/pages/Auth/Login/Login.tsx";
import Profile from "@/pages/Account/Profile/Profile.tsx";
import { requireAuth } from "@/app/requireAuth.ts";
import CreateFundraiser from "@/pages/Account/Fundraisers/FundraiserForm/CreateFundraiser/CreateFundraiser.tsx";
import Donations from "@/pages/Account/Donations/Donations.tsx";
import EditFundraiser from "@/pages/Account/Fundraisers/FundraiserForm/EditFundraiser/EditFundraiser.tsx";
import AddUpdate from "@/pages/Account/Fundraisers/Update/AddUpdate.tsx";
import { Fundraiser } from "@/pages/Fundraiser/Fundraiser.tsx";
import Fundraisers from "@/pages/Account/Fundraisers/Fundraisers.tsx";
import { getUser } from "@/app/getUser.ts";
import AccountLayout from "@/pages/Account/AccountLayout.tsx";

export const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    loader: async () => {
      try {
        return await queryClient.ensureQueryData({
          queryKey: ["user"],
          queryFn: getUser,
        });
      } catch {
        queryClient.setQueryData(["user"], null);
        return null;
      }
    },

    children: [
      {
        index: true,
        Component: Home,
        loader: async () => {
          return await queryClient.ensureQueryData({
            queryKey: ["newest"],
            queryFn: homeApi.getNewest,
          });
        },
      },
      {
        path: "fundraiser/:username/:slug",
        Component: Fundraiser,
        errorElement: (
          <div style={{ padding: "40px", color: "white", textAlign: "center" }}>
            Збір не знайдено (404)
          </div>
        ),
        loader: async ({ params }) => {
          const { username, slug } = params;

          if (!username || !slug) {
            throw redirect("/");
          }
          return await queryClient.ensureQueryData({
            queryKey: ["fundraiser", username, slug],
            queryFn: () => homeApi.getByUsernameAndSlug(username, slug),
          });
        },
      },
      {
        path: "register",
        Component: Register,
      },
      {
        path: "login",
        Component: Login,
      },
      {
        path: "account",
        Component: AccountLayout,
        loader: requireAuth,
        children: [
          {
            index: true,
            loader: () => redirect("profile"),
          },
          {
            path: "profile",
            Component: Profile,
          },
          {
            path: "donations",
            Component: Donations,
          },
          {
            path: "fundraisers",
            children: [
              {
                index: true,
                Component: Fundraisers,
              },
              {
                path: "create",
                Component: CreateFundraiser,
              },
              {
                path: "update/:id",
                Component: AddUpdate,
              },
              {
                path: "edit/:id",
                Component: EditFundraiser,
              },
            ],
          },
        ],
      },
    ],
  },
]);
