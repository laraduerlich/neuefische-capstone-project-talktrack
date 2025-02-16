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
                'custom-gradient': 'linear-gradient(0deg, rgba(226,115,102,1) 0%, rgba(215,173,144,1) 100%);',
            },
        },
    },
    plugins: [],
};

export default config;
