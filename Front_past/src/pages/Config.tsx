
import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '@/components/NavBar';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Bell, User, ArrowLeft } from 'lucide-react';

const Config: React.FC = () => {
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
          <h1 className="text-3xl font-bold text-vagacerta-blue">Configurações</h1>
        </div>

        <div className="grid grid-cols-1 gap-6 max-w-3xl mx-auto">
          <Card className="shadow-sm hover:shadow transition-shadow">
            <CardHeader className="flex flex-row items-center space-y-0 pb-2">
              <User className="h-5 w-5 text-vagacerta-blue mr-2" />
              <CardTitle className="text-xl">Sua Conta</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-muted-foreground mb-4">
                Gerencie suas informações pessoais, acesso e dados da conta.
              </p>
              <Button 
                className="w-full bg-vagacerta-blue hover:bg-blue-600"
                onClick={() => navigate('/perfil')}
              >
                Acessar Configurações de Conta
              </Button>
            </CardContent>
          </Card>

          <Card className="shadow-sm hover:shadow transition-shadow">
            <CardHeader className="flex flex-row items-center space-y-0 pb-2">
              <Bell className="h-5 w-5 text-vagacerta-blue mr-2" />
              <CardTitle className="text-xl">Notificações</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-muted-foreground mb-4">
                Configure como e quando deseja receber alertas e notificações.
              </p>
              <Button 
                className="w-full bg-vagacerta-blue hover:bg-blue-600"
                onClick={() => navigate('/notificacao')}
              >
                Gerenciar Notificações
              </Button>
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

export default Config;
