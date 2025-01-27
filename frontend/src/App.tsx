import './App.css'
import Header from "./component/Header.tsx";
import {Route, Routes} from "react-router-dom";
import LinkPage from "./page/LinkPage.tsx";
import LandingPage from "./page/LandingPage.tsx";
import DashboardPage from "./page/DashboardPage.tsx";
import SummaryPage from "./page/SummaryPage.tsx";
import Footer from "./component/Footer.tsx";

function App() {

  return (
    <>
        <div>
            <Header />
            <div>
                <Routes>
                    <Route path={"/"} element={<LandingPage />} />
                    <Route path={"/home"} element={<DashboardPage />} />
                    <Route path={"/newsummary"} element={<LinkPage />} />
                    <Route path={"/summary/:id"} element={<SummaryPage />} />
                </Routes>
            </div>
            <Footer />
        </div>
    </>
  )
}

export default App
