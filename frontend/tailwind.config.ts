import type { Config } from 'tailwindcss';

const config: Config = {
    content: [
        "./src/**/*.{js,ts,jsx,tsx}",
        "./pages/**/*.{js,ts,jsx,tsx}",
        "./components/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            backgroundImage: {
                'custom-gradient': 'linear-gradient(0deg, rgba(129,140,248,1) 0%, rgba(199,210,254,1) 100%)',
            },
        },
    },
    plugins: [],
};

export default config;
