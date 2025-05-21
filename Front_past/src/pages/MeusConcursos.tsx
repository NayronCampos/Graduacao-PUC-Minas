import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '@/components/NavBar';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { ArrowLeft, BookOpen, CalendarCheck, FileText, BookMarked } from 'lucide-react';

const MeusConcursos: React.FC = () => {
  const navigate = useNavigate();

  // Dados fictícios para os concursos do usuário
  const meusConcursos = [
    {
      id: 1,
      titulo: 'Concurso Público TRT 10ª Região',
      cargo: 'Técnico Judiciário',
      dataInscricao: '10/03/2025',
      dataProva: '15/06/2025',
      status: 'Inscrição Confirmada'
    },
    {
      id: 2,
      titulo: 'Concurso INSS',
      cargo: 'Analista do Seguro Social',
      dataInscricao: '05/05/2025',
      dataProva: '30/07/2025',
      status: 'Aguardando Confirmação'
    },
    {
      id: 3,
      titulo: 'Prefeitura Municipal',
      cargo: 'Auditor Fiscal',
      dataInscricao: '20/02/2025',
      dataProva: '25/04/2025',
      status: 'Inscrição Confirmada'
    }
  ];

  return (
    <div className="min-h-screen flex flex-col bg-gray-50">
      <NavBar />
      
      <div className="container mx-auto px-4 pt-24 pb-16 flex-1">
        <div className="flex items-center mb-8">
          <Button 
            variant="ghost" 
            className="mr-4" 
            onClick={() => navigate(-1)}
          >
            <ArrowLeft className="mr-2" size={20} />
            Voltar
          </Button>
          <h1 className="text-3xl font-bold text-vagacerta-blue">Meus Concursos</h1>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {meusConcursos.map((concurso) => (
            <Card key={concurso.id} className="shadow-sm hover:shadow-md transition-shadow">
              <CardHeader className="bg-vagacerta-blue text-white py-4">
                <h2 className="text-xl font-semibold">{concurso.titulo}</h2>
                <p className="text-sm opacity-90">{concurso.cargo}</p>
              </CardHeader>
              <CardContent className="p-6">
                <div className="space-y-4">
                  <div className="flex items-center">
                    <CalendarCheck className="h-5 w-5 mr-3 text-vagacerta-blue" />
                    <div>
                      <p className="text-sm text-gray-500">Data de Inscrição</p>
                      <p className="font-semibold">{concurso.dataInscricao}</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center">
                    <BookMarked className="h-5 w-5 mr-3 text-vagacerta-blue" />
                    <div>
                      <p className="text-sm text-gray-500">Data da Prova</p>
                      <p className="font-semibold">{concurso.dataProva}</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center">
                    <FileText className="h-5 w-5 mr-3 text-vagacerta-blue" />
                    <div>
                      <p className="text-sm text-gray-500">Status</p>
                      <p className="font-semibold">{concurso.status}</p>
                    </div>
                  </div>
                  
                  <div className="flex justify-between mt-6">
                    <Button 
                      variant="outline" 
                      size="sm"
                      className="border-vagacerta-blue text-vagacerta-blue hover:bg-vagacerta-blue hover:text-white"
                    >
                      Ver Detalhes
                    </Button>
                    <Button 
                      variant="outline" 
                      size="sm"
                      className="border-vagacerta-blue text-vagacerta-blue hover:bg-vagacerta-blue hover:text-white"
                    >
                      Material de Estudo
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        {meusConcursos.length === 0 && (
          <div className="max-w-3xl mx-auto">
            <Card className="shadow-sm">
              <CardContent className="p-8 text-center">
                <div className="flex flex-col items-center justify-center py-12">
                  <BookOpen className="text-gray-400 mb-4" size={48} />
                  <p className="text-gray-500 text-lg mb-8">Você ainda não se inscreveu em nenhum concurso!</p>
                  <Button 
                    className="bg-vagacerta-blue hover:bg-vagacerta-darkBlue"
                    onClick={() => navigate('/concursos')}
                  >
                    Explorar Concursos
                  </Button>
                </div>
              </CardContent>
            </Card>
          </div>
        )}
      </div>

      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default MeusConcursos;
