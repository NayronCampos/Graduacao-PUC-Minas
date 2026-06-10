import React from 'react';
import { 
  GraduationCap, 
  FileEdit, 
  Calendar, 
  Library, 
  BookPlus, 
  ListPlus 
} from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import NavBar from '@/components/NavBar';
import FeatureCard from '@/components/FeatureCard';
import { useToast } from '@/components/ui/use-toast';

const Index = () => {
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleFeatureClick = (route: string) => {
    navigate(route);
  };
  
  return (
    <div className="min-h-screen flex flex-col bg-vagacerta-bg">
      <NavBar />
      
      <main className="container mx-auto px-4 pt-24 pb-12 flex-1">
        <div className="max-w-4xl mx-auto">
          <h1 className="text-3xl font-bold text-center text-vagacerta-blue mb-2 animate-fade-in">
            Bem-vindo ao VAGA CERTA
          </h1>
          <p className="text-gray-600 text-center mb-12 animate-fade-in">
            Sua plataforma completa para preparação em concursos públicos
          </p>
          
          <section className="mb-12">
            <h2 className="text-xl font-semibold mb-6 text-gray-700">Recursos Principais</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              <FeatureCard 
                title="CONCURSOS" 
                icon={GraduationCap} 
                variant="primary"
                onClick={() => handleFeatureClick("/concursos")}
              />
              <FeatureCard 
                title="SIMULADOS" 
                icon={FileEdit} 
                variant="primary"
                onClick={() => handleFeatureClick("/simulados")}
              />
              <FeatureCard 
                title="CRONOGRAMA" 
                icon={Calendar} 
                variant="primary"
                onClick={() => handleFeatureClick("/cronogramas")}
              />
              <FeatureCard 
                title="BIBLIOTECA" 
                icon={Library} 
                variant="primary"
                onClick={() => handleFeatureClick("/biblioteca")}
              />
            </div>
          </section>
          
          <section>
            <h2 className="text-xl font-semibold mb-6 text-gray-700">Cadastros</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FeatureCard 
                title="CADASTRO DE LIVROS" 
                icon={BookPlus} 
                variant="secondary"
                onClick={() => handleFeatureClick("/cadlivros")}
              />
              <FeatureCard 
                title="CADASTRO DE CONCURSOS" 
                icon={ListPlus} 
                variant="secondary"
                onClick={() => handleFeatureClick("/cadconcursos")}
              />
            </div>
          </section>
        </div>
      </main>
      
      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default Index;
