import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '@/components/NavBar';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { ArrowLeft, Bell } from 'lucide-react';

const Notificacao: React.FC = () => {
  const navigate = useNavigate();

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
          <h1 className="text-3xl font-bold text-vagacerta-blue">Notificações</h1>
        </div>

        <div className="max-w-3xl mx-auto">
          <Card className="shadow-sm">
            <CardHeader className="bg-vagacerta-blue text-white text-center py-4">
              <h2 className="text-xl font-semibold">NOTIFICAÇÕES</h2>
            </CardHeader>
            <CardContent className="p-8 text-center">
              <div className="flex flex-col items-center justify-center py-12">
                <Bell className="text-gray-400 mb-4" size={48} />
                <p className="text-gray-500 text-lg mb-8">NADA POR AQUI!</p>
                <Button className="bg-blue-500 hover:bg-blue-600">
                  LIMPAR
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>

      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default Notificacao;
