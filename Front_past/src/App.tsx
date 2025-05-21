
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import Concursos from "./pages/Concursos";
import Simulados from "./pages/Simulados";
import Cronogramas from "./pages/Cronogramas";
import Biblioteca from "./pages/Biblioteca";
import CadastroLivros from "./pages/CadastroLivros";
import CadastroConcursos from "./pages/CadastroConcursos";
import Perfil from "./pages/Perfil";
import Config from "./pages/Config";
import Notificacao from "./pages/Notificacao";
import MeusConcursos from "./pages/MeusConcursos";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/concursos" element={<Concursos />} />
          <Route path="/simulados" element={<Simulados />} />
          <Route path="/cronogramas" element={<Cronogramas />} />
          <Route path="/biblioteca" element={<Biblioteca />} />
          <Route path="/cadlivros" element={<CadastroLivros />} />
          <Route path="/cadconcursos" element={<CadastroConcursos />} />
          <Route path="/perfil" element={<Perfil />} />
          <Route path="/config" element={<Config />} />
          <Route path="/notificacao" element={<Notificacao />} />
          <Route path="/meusconcursos" element={<MeusConcursos />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
