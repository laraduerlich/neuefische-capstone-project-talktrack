
export default function Header() {

    const navLinks = [
        { name: "Dashboard", path: "/home" },
        { name: "Neu", path: "/summary/new"}
    ];

    return (
        <>
            <header className="backdrop-blur-md bg-white/30 p-6 text-gray-800
            {/*[box-shadow:0px_6px_12px_rgba(244,67,54,0.2)]*/}">
                <div className="container flex justify-between h-16 mx-auto">
                    <a
                        rel="noopener noreferrer"
                        href="/"
                        aria-label="Back to homepage"
                        className="flex items-center"
                    >
                        {/* App Logo */}
                        <img
                            src="/app-logo.png"
                            alt="TalkTrack"
                            className="w-16 h-16 object-contain"
                        />
                        {/* App Name */}
                        <img
                            src="/app-name.png"
                            alt="App Name"
                            className="ml-4 hidden md:block h-24 object-contain"
                        />
                    </a>


                    {/* Navigation */}
                    <ul className="items-stretch space-x-3 md:flex">
                        {navLinks.map((link) => (
                            <li key={link.name} className="flex">
                                <a
                                    rel="noopener noreferrer"
                                    href={link.path}
                                    className="flex items-center px-4 -mb-1 border-b-2 border-transparent hover:border-gray-800"
                                >
                                    {link.name}
                                </a>
                            </li>
                        ))}
                    </ul>

                </div>
            </header>
        </>
    )
}
