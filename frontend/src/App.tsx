import './App.css'
import Header from "./component/Header.tsx";
import {Route, Routes} from "react-router-dom";
import UploadPage from "./page/UploadPage.tsx";
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
                    <Route path={"/summary/new"} element={<UploadPage />} />
                    <Route path={"/summary/:id"} element={<SummaryPage />} />
                </Routes>
            </div>
            <Footer />
        </div>
    </>
  )
}

export default App
